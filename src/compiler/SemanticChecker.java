package compiler;

import it.m2j.IdType;
import it.m2j.Operator;
import it.m2j.SymbolDesc;

import java.io.PrintWriter;
import java.util.ArrayList;

import ast.*;

public class SemanticChecker extends Visitor
{
    //pointer to the global symbol table
    private SymbolTable sTable;    

    //the class that we are currently checking
    //private ClassSymbol curClass = null;

    public SemanticChecker(SymbolTable sTable, PrintWriter out)
    {
        super(out);
        this.sTable = sTable;
    }

    public Object visit(DeclNode node)
    {
    	return null;
    }

    //true if we are checking the lhs of an assignment
    private boolean inAssignment = false;
    
    public Object visit(AssignNode node)
    {    	        	    	
    	IdType type1 = (IdType) node.visitVar(this);
    	IdType type2 = (IdType) node.visitValue(this);
    	
    	if (type1 == IdType.ERR || type2 == IdType.ERR)
        	error("Error in assignment",node);

        // If operands are of two different types and they aren't numerics print error
        else if ( !type1.toString().equals(type2.toString()) ) 
        {                 
        	if (!(type1.IsNumeric() == true && type2.IsNumeric() == true)) //If both are numerics don't alert: PROM
        		error("Can't assign " + type2.toString() + " to " + type1.toString(), node );
            else if(type1 == IdType.INT && type2 == IdType.FLOAT)
            	error("Can't assign " + type2.toString() + " to " + type1.toString() + ". Required cast.", node );                	                
        }

    	return null;
    }

    public Object visit(VarNode node)
    {    	    	
    	IdType t = sTable.getVariableType(node.getName(), node.getBlockNumber()); 

    	return t;
    }

    public Object visit(BlockNode node)
    {    	
        node.visitChildren(this);
    	
    	return null;
    }

    public Object visit(NewNode node)
    {
//        checkSuperCall(node);
//
//        Symbol className = sTable.get(node.getClassName());
//
//        //check the class exists
//        if (className == null || !(className instanceof ClassSymbol))
//        {
//            error("No such class: '" + node.getClassName() + "'. ", node);
//            return sTable.get("unknown");
//        }
//        ClassSymbol classSymbol = (ClassSymbol)className;
//
//        //check the parameters
//        String[] typeNames = ParamUtils.makeTypeArray(node.visitParams(this));
//
//        //check an approriate constructor exists
//        MethodSymbol methodSymbol = classSymbol.getConstructor(typeNames);
//        if (methodSymbol == null)
//        {
//            error("Constructor not found: '" + classSymbol.getName() + " (" + ParamUtils.makeList(typeNames) + ")'. ", node);
//            return(className);
//        }
//
//        //check we have access to it
//        checkAccess(methodSymbol, node);
//
//        node.setSymbol(methodSymbol);
//        node.setClassSymbol(classSymbol);
//
//        return className;
    	return null;
    }

    //only checks the method exists statically, the actual method to call is
    //determined at run time
    public Object visit(FuncCallNode node)
    {
    	//Node[] paramsCall = node.getParams(); //Call Params
    	Object[] paramsCall = node.visitParams(this); //Call Params

    	ArrayList<SymbolDesc> funcDesc = sTable.getSpecific(node.getName(), IdType.FUNCTION);
    	
    	if(funcDesc.size() > 0)
    	{
    		ArrayList<IdType> paramsDecl = funcDesc.get(0).getParamList();//Declaration Params
    		
    		if(paramsDecl.size() == paramsCall.length)
    		{    		    			
        		for(int i=0; i< paramsCall.length; i++)
            	{           			
        			System.out.println("ToString1: " + paramsCall[i]);
        			        			
            		if(! paramsCall[i].toString().equals(paramsDecl.get(i).toString()) )
        			{
            			error("Function \""+node.getName()+"\": expected " + paramsDecl.get(i).toString() + ", found " + paramsCall[i].toString() + " ", node);
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

    	return sTable.getFunctionType(node.getName());


    	//        //check the parameters
//        String[] typeNames = ParamUtils.makeTypeArray(node.visitParams(this));
//
//        //check the target
//        Symbol target = (Symbol)node.visitTarget(this);
//        if (!(target instanceof ClassSymbol))
//        {
//            error("Method not defined: '" + node.getName() + " (" + ParamUtils.makeList(typeNames) + ")'. ", node);
//            return sTable.get("unknown");
//        }
//
//        //check the target has a method with the given parameters
//        ClassSymbol targetClass = (ClassSymbol)target;
//        MethodSymbol method = targetClass.getMethod(node.getName(), typeNames);
//        if (method == null)
//        {
//            error("Method not defined: '" + node.getName() + " (" + ParamUtils.makeList(typeNames) + ")'. ", node);
//            return sTable.get("unknown");
//        }
//
//        //check we have access
//        checkAccess(method, node);
//
//        //set the static method in the node
//        node.setSymbol(method);
//
//        //return the method's return type
//        Symbol returnType = sTable.get(method.getType());
//        if (returnType == null)
//            return sTable.get("unknown"); //the error will be caught when the method is resolved
//        else
//            return returnType;    	
    }

    public Object visit(ArgNode node)
    {
//        //check that the parameter's type exists
//        Symbol type = sTable.get(node.getType());
//        Symbol arg = curTable.get(node.getName());
//
//        //variable should have been added to symbol table in the first pass
//        if (arg == null)
//            throw new RuntimeException("Parameter should be in symbol table: " + node.getName());
//
//        //check that the type exists
//        if (type == null)
//        {
//            error("Type '" + node.getType() + "' does not exist: ", node);
//            arg.resolveType(sTable.get("unknown"));
//        }
//        else
//            arg.resolveType(type);
//
//        return sTable.get("void");
    	return null;
    }

    //the return type for the current node
    private IdType returnType = null;

    //weather there is a return type in the method
    private boolean returned = false;

    public Object visit(FunctionNode node)
    {
    	returnType = node.getType();

        visitParamsAndBody(node, returnType);

        //make sure there's at elast one return statement if the type is not void
        if (!returned && node.getType() != IdType.VOID)
            error("Function does not return. ", node);
    	
        return returnType;
    }

    //visits the parameters and body of a function
    private void visitParamsAndBody(FunctionNode node, IdType returnType)
    {
        //check the parameters
        node.visitParams(this);

        //setup return type checking
        returned = false;
        //this.returnType = returnType;

        node.visitBody(this);            	    	
    }


    public Object visit(ListNode node)
    {
        node.visitChildren(this);

    	return null;
    }

    public Object visit(WhileNode node)
    {    	
    	IdType type = (IdType) node.visitTest(this);    		
    	IdType tRet = IdType.ERR;
    	
    	if (type != IdType.BOOL)
    		error("Test expression in while statement must be of type boolean, found: '" + type + "' in expression: ", node);
    	else
    		tRet = type;

        node.visitWhile(this);
        
        return tRet;        
    }    
    
    public Object visit(IfNode node)
    {    	
    	IdType type = (IdType) node.visitTest(this);    		
    	IdType tRet = IdType.ERR;
    	
    	if (type != IdType.BOOL)
    		error("Test expression in if statement must be of type boolean, found: '" + type + "' in expression: ", node);
    	else
    		tRet = type;

        node.visitThen(this);
        
        return tRet;        
    }
    

	public Object visit(IfElseNode node) 
	{
    	IdType type = (IdType) node.visitTest(this);    		
    	IdType tRet = IdType.ERR;
    	
    	if (type != IdType.BOOL)
    		error("Test expression in if statement must be of type boolean, found: '" + type + "' in expression: ", node);
    	else
    		tRet = type;

        node.visitThen(this);
        
        node.visitElse(this);
        
        return tRet;
	}
    

//    public Object visit(PrintNode node)
//    {
//        checkSuperCall(node);
//
//        Symbol valueType = (Symbol)node.visitValue(this);
//
//        //type of the expression in the print statement must not be void
//        if (valueType == sTable.get("void"))
//            error("Argument to print statement must have type: found 'void'. ", node);
//
//        node.setType(valueType.getName());
//
//        return sTable.get("void");
//    }

    public Object visit(ReturnNode node)
    {
        IdType valueType = (IdType) node.visitValue(this);

        if (valueType == IdType.NULL)// "return;"
        {
            if (returnType != IdType.VOID)
                error("Return type does not match function declaration expected: '" + returnType + "' found: 'void'. ", node);
        }
        else
        {                        
            if (valueType != returnType)
                error("Return type does not match function declaration expected: '" + returnType + "' found: '" + (IdType)valueType + "'. ", node);            
        }

        returned = true;

    	return valueType;
    }

	public Object visit(CastNode node) {
		IdType type = (IdType) node.visitChild(this);
		
		
		if (type == IdType.FLOAT || type == IdType.INT)
			return IdType.INT;
		else 
			error("Cannot cast " + type + " to int.", node);
		return IdType.ERR;

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
    private IdType visitBinaryOp(Operator op, BinaryNode node)
    {
    	IdType type1 = (IdType) node.visitLeft(this);    	
    	IdType type2 = (IdType) node.visitRight(this);    	

		IdType t = IdType.ERR;

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

		return t;
    }
    
    //-------------------------------------------------------------------------- 
    
    
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
    private IdType visitUnaryOp(Operator op, UnaryNode node)
    {
    	IdType type = (IdType) node.visitChild(this);
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
            
        return tRet;    	
    }
    
    //--------------------------------------------------------------------------

    public Object visit(BoolNode node)
    {    
    	return node.getType();
    }

    public Object visit(IntNode node)
    {
    	return node.getType();
    }

    public Object visit(StringNode node)
    {     
    	return node.getType();
    }

	public Object visit(FloatNode node) {
		return node.getType();
	}

	public Object visit(PrintNode node) {
		return null;
	}

	public Object visit(NullNode node)
	{
		return node.getType();
	}
	
	public Object visit(NegNode node) 
	{
		return null;
	}
}
