package compiler;

import aux.*;
import java.util.ArrayList;

import utils.StuffCreator;

import ast.*;

public class SemanticChecker extends Visitor
{
    //pointer to the global symbol table
    private SymbolTable sTable;    

    public SemanticChecker(SymbolTable sTable)
    {
        this.sTable = sTable;
    }

    public Object visit(DeclNode node)
    {
    	return null;    	
    }
    
    public Object visit(AssignNode node)
    {    	        	    	
    	//LEFT HAND
    	NodeInfo left = (NodeInfo)node.visitVar(this);    	
    	IdType type1 = left.getType();
    	int dim1 = left.getDim();
    	
    	//RIGHT HAND
    	NodeInfo right = (NodeInfo) node.visitValue(this);
    	IdType type2 = right.getType();
    	int dim2 = right.getDim();

    	if (dim1 != dim2)
    		error("Type mismatch. Cannot convert from " + type2 + StuffCreator.getBrackets(dim2) + " to " + type1 + StuffCreator.getBrackets(dim1) + " ", node);
    	else
    	{
	    	if (type1 == IdType.ERR || type2 == IdType.ERR)
	        	error("Error in assignment",node);
	
	        // If operands are of two different types and they aren't numerics print error
	        else if ( !type1.toString().equals(type2.toString()) ) 
	        {                 
	        	if (!(type1.IsNumeric() == true && type2.IsNumeric() == true)) //If both are numerics don't alert: PROM
	        		error("Can't assign " + type2.toString() + " to " + type1.toString() + " ", node );
	            else if(type1 == IdType.INT && type2 == IdType.FLOAT)
	            	error("Can't assign " + type2.toString() + " to " + type1.toString() + ". Required cast.", node );                	                
	        }
    	}
    	
    	return null;
    }
    
    public Object visit(BlockNode node)
    {    	
        node.visitChildren(this);
    	
    	return null;
    }

	public Object visit(FunctionExtNode node) {
		// TODO Auto-generated method stub
		return null;
	}
    
    public Object visit(FuncCallNode node)
    {    	
    	Object[] paramsCall = node.visitParams(this); //Call Params

    	ArrayList<SymbolDesc> funcDesc = sTable.getSpecific(node.getName(), IdType.FUNCTION);
    	
    	if(funcDesc.size() > 0)
    	{
    		ArrayList<NodeInfo> paramsDecl = funcDesc.get(0).getParamList();//Declaration Params
    		    		
    		if(paramsDecl.size() == paramsCall.length)
    		{    		    			
        		for(int i=0; i< paramsCall.length; i++)
            	{
        			NodeInfo info = (NodeInfo)paramsCall[i];

        			if( (info.getType() != paramsDecl.get(i).getType()) || info.getDim() != paramsDecl.get(i).getDim())
        			{
            			error("Function \""+node.getName()+"\": expected " + paramsDecl.get(i).toString() + ", found " + info.toString() + " ", node);
        			}
            	}       			
    		}
    		else
    		{
    			if(paramsCall.length > 0)
    			{
					String args=paramsCall[0].toString();

					for(int i=1;i<paramsCall.length;i++)
						args= args + ", " + paramsCall[i].toString();

	                error("Function \""+node.getName()+"\" is not applicable for the arguments: (" + args + ").", node);			                    				
    			}
    			else
    				error("Function \""+node.getName()+"\" is not applicable without arguments.", node);
    		}
    	}

    	//return sTable.getFunctionType(node.getName());
    	return new NodeInfo(sTable.getFunctionType(node.getName()), 0);
    }

    public Object visit(ArgNode node)
    {
    	return null;
    }

    //the return type for the current node
    private IdType returnType = null;
    private int returnDim = 0;

    //weather there is a return type in the function
    private boolean returned = false;

    public Object visit(FunctionNode node)
    {
    	returnType = node.getType();
    	returnDim = node.getDimension();

        visitParamsAndBody(node, returnType);

        //make sure there's at elast one return statement if the type is not void
        if (!returned && node.getType() != IdType.VOID)
            error("Function does not return. ", node);
    	
        return new NodeInfo(returnType, returnDim);
    }

    //visits the parameters and body of a function
    private void visitParamsAndBody(FunctionNode node, IdType returnType)
    {
        //check the parameters
        node.visitParams(this);

        //setup return type checking
        returned = false;

        node.visitBody(this);            	    	
    }

    public Object visit(ListNode node)
    {
        node.visitChildren(this);
    	
        return null;
    }

    public Object visit(WhileNode node)
    {    	
    	NodeInfo info =(NodeInfo) node.visitTest(this);
    	IdType type = info.getType();
    	
    	if (type != IdType.BOOL)
    		error("Test expression in while statement must be of type boolean, found: '" + type + "' in expression: ", node);

        node.visitWhile(this);
        
        return null;        
    }    
    
    public Object visit(IfNode node)
    {    	
    	NodeInfo info =(NodeInfo) node.visitTest(this); 
    	IdType type = info.getType();     		

    	if (type != IdType.BOOL)
    		error("Test expression in if statement must be of type boolean, found: '" + type + "' in expression: ", node);

        node.visitThen(this);

        return null;        
    }

	public Object visit(IfElseNode node) 
	{
		NodeInfo info =(NodeInfo) node.visitTest(this);
    	IdType type = info.getType();    		
    	
    	if (type != IdType.BOOL)
    		error("Test expression in if statement must be of type boolean, found: '" + type + "' in expression: ", node);

    	node.visitThen(this);        
        node.visitElse(this);
        
        return null;
	}

    public Object visit(ReturnNode node)
    {
        NodeInfo retInfo = (NodeInfo) node.visitValue(this);
    	IdType valueType = retInfo.getType();
    	int valueDim = retInfo.getDim();
    	
        if (valueType == IdType.NULL)// "return;"
        {
            if (returnType != IdType.VOID)
                error("Return type does not match function declaration expected: '" + returnType + "' found: 'void'. ", node);
        }
        else
        {                        
            if (valueType != returnType)
                error("Return type does not match function declaration expected: '" + returnType + "' found: '" + (IdType)valueType + "'. ", node);
            else
            {
            	if(valueDim != returnDim)
            		error("Return type does not match function declaration expected: '" + returnType + StuffCreator.getBrackets(returnDim) + "' found: '" + (IdType)valueType + StuffCreator.getBrackets(valueDim) + "'. ", node);
            }
        }

        returned = true;
        
        return retInfo;
    }

	public Object visit(CastNode node) {
		NodeInfo info = (NodeInfo)node.visitChild(this);
		
		
		if (info.getType() == IdType.FLOAT || info.getType() == IdType.INT)
			return new NodeInfo(IdType.INT, info.getDim());
		else 
			error("Cannot cast " + info.getType() + " to int.", node);
		return new NodeInfo(IdType.ERR, info.getDim());

	}
	
	public Object visit(SimpleVarNode node) 
	{
    	SymbolDesc varDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());
    	NodeInfo info = null;
    	
    	if(varDesc == null)
    	{
    		error("Variable not declared: ", node);
    		info = new NodeInfo(IdType.ERR, 0);
    	}
    	else
    		info = new NodeInfo(varDesc.getType(), varDesc.getDim());    	    	
    	
    	return info;
	}	
	
	public Object visit(ArrayNode node) {
    	SymbolDesc varDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());
    	NodeInfo info = null;
    	
    	if(varDesc == null)
    	{
    		error("Variable not declared: ", node);
    		info = new NodeInfo(IdType.ERR, 0);
    	}
    	else
    		info = new NodeInfo(varDesc.getType(), varDesc.getDim());    	    	
    	
    	
    	return info;	
	}
	
	
    //------------------------------------- BINARY OPERATORS ------------------------------------- 
    public Object visit(ModNode node)
    {
    	return visitBinaryOp(Operator.MOD, node);
    }
    
    public Object visit(DivNode node)
    {
        return visitBinaryOp(Operator.DIV, node);
    }

    public Object visit(AddNode node)
    {    	
        return visitBinaryOp(Operator.PLUS, node);    	
    }

    public Object visit(SubNode node)
    {
        return visitBinaryOp(Operator.DIFF, node);    	
    }

    public Object visit(MulNode node)
    {
        return visitBinaryOp(Operator.MUL, node);    	
    }

    public Object visit(OrNode node)
    {
        return visitBinaryOp(Operator.OR, node);    	
    }

    public Object visit(AndNode node)
    {
    	return visitBinaryOp(Operator.AND, node);    	
    }
    
    public Object visit(EqNode node)
    {
    	return visitBinaryOp(Operator.EQ, node);
    }

    public Object visit(NotEqNode node)
    {
    	return visitBinaryOp(Operator.NEQ, node);
    }
    
    public Object visit(LTNode node)
    {
    	return visitBinaryOp(Operator.LT, node);
    }

    public Object visit(LETNode node)
    {
    	return visitBinaryOp(Operator.LET, node);
    }    
    
    public Object visit(GTNode node)
    {
    	return visitBinaryOp(Operator.GT, node);
    }

    public Object visit(GETNode node)
    {
    	return visitBinaryOp(Operator.GET, node);
    }    
    
    //shared functionality for &&, ||, +, -, / and * operators
    private NodeInfo visitBinaryOp(Operator op, BinaryNode node)
    {
    	NodeInfo infoRet;
    	
    	//Left Hand
    	NodeInfo left = (NodeInfo) node.visitLeft(this);
    	IdType type1 = left.getType();
    	int dim1 = left.getDim();
    	
    	//Right Hand
    	NodeInfo right = (NodeInfo) node.visitRight(this);
    	IdType type2 = right.getType();
    	int dim2 = right.getDim();

    	IdType t = IdType.ERR;

    	if (dim1 != dim2)
    		error("Type mismatch. Cannot convert from " + type1 + StuffCreator.getBrackets(dim1) + " to " + type2 + StuffCreator.getBrackets(dim2), node);
    	else
    	{
	    	if (type1 == IdType.ERR || type2 == IdType.ERR)
	    	{
	        	error("Error in expression",node);
	        	infoRet = new NodeInfo(t, Math.max(dim1, dim2)); // convenzione
	        	
	    		return infoRet;
	    	}
	    	
			switch(op)
			{
				case PLUS: 
				case DIFF:
				case MUL:
				case DIV:
					
					if(type1.IsNumeric() == true && type2.IsNumeric() == true)
					{
						if (type1 == type2) 
							t = type1;
						else
							t = IdType.FLOAT; //PROM
					}
					else
						error("Incomparable types: " + type1.toString() + " to " + type2.toString(), node );					
					break;
					
				case MOD:
					if(type1.IsNumeric() == true && type2.IsNumeric() == true)
						t = IdType.INT;
					else
						error("Incomparable types: " + type1.toString() + " to " + type2.toString(), node );
	
					break;
				
				case AND:
				case OR:
					if( (type1 == type2) && (type1 == IdType.BOOL) )
						t = IdType.BOOL;
					else
						error("Incomparable types: " + type1.toString() + " to " + type2.toString(), node );															
					break;
	
				case EQ:
				case NEQ:
					if (type1 != IdType.STRING && type2 != IdType.STRING)
						t = IdType.BOOL;
					else
						error("Incomparable types: " + type1.toString() + " to " + type2.toString(), node );						
					break;
	
				case LT:
				case LET:
				case GT:
				case GET:
					if(type1.IsNumeric() == true && type2.IsNumeric() == true)
						t = IdType.BOOL;
					else
						error("Incomparable types: " + type1.toString() + " to " + type2.toString(), node );						
	
					break;
			}
    	}

    	infoRet = new NodeInfo(t, Math.max(dim1, dim2)); // convenzione
    	
		return infoRet;
    }
    
    //------------------------------------- UNARY OPERATORS -------------------------------------
    
    public Object visit(SignNode node)
    {
        return visitUnaryOp(Operator.SIGN, node);
    }

    public Object visit(NotNode node)
    {
        return visitUnaryOp(Operator.NOT, node);    	
    }
	
    //shared functionality for - and ! operators
    private NodeInfo visitUnaryOp(Operator op, UnaryNode node)
    {    	    	    	
    	NodeInfo left = (NodeInfo) node.visitChild(this);
    	IdType type = left.getType();
    	int dim = left.getDim();
    	    	
    	IdType tRet = IdType.ERR;
    	
    	switch(op)
		{
			case NOT:
				if (type == IdType.BOOL)
					tRet = type;
				else
					error("Argument to operator " + op + " must be of type " + IdType.BOOL + ", found: '" + type + "' in expression: ", node);
				
				break;
			case SIGN:
				if(type == IdType.INT || type == IdType.FLOAT)
					tRet = type;
				else
					error("Argument to operator " + op + " must be int or float, found: '" + type + "' in expression: ", node);				
				break;
		}    	    	
            
        return new NodeInfo(tRet, dim);    	
    }
    
    //--------------------------------------------------------------------------

    public Object visit(BoolNode node)
    {    
    	NodeInfo info = new NodeInfo(node.getType(), 0);
    	return info;
    }

    public Object visit(IntNode node)
    {
    	NodeInfo info = new NodeInfo(node.getType(), 0);
    	return info;
    }

    public Object visit(StringNode node)
    {     
    	NodeInfo info = new NodeInfo(node.getType(), 0);
    	return info;
    }

	public Object visit(FloatNode node) 
	{
		NodeInfo info = new NodeInfo(node.getType(), 0);
		return info;
	}

	public Object visit(NullNode node)
	{
		NodeInfo info = new NodeInfo(node.getType(), 0);
		return info;
	}
	
	public Object visit(ArrayNewNode node) 
	{
		node.visitDim(this);
		
		return (new NodeInfo(node.getType(), node.getDimension()));	
	}
	
	public Object visit(ArraySizeNode node)
	{			
		NodeInfo info = (NodeInfo) node.visitExpr(this);				
		
		if(info.getType() != IdType.INT)
			error("Array argument must be int.", node);
		
		return null;
	}
	
	public Object visit(ArrayCallNode node) 
	{
		int varDim;
		int nodeDim;		
		
		NodeInfo varInfo = (NodeInfo) node.visitVar(this);		
		node.visitDim(this);	

		varDim = varInfo.getDim();
		nodeDim = node.getDimension();			
		
		if(nodeDim > varDim)
		{
			//error("Variable "+ node.getName() + " exceeds dimension.", node);
			return (new NodeInfo(IdType.ERR, nodeDim));
		}		

		NodeInfo info = new NodeInfo(varInfo.getType(), varDim - nodeDim);
    	return info;			
	}
}