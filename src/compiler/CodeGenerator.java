package compiler;

import it.m2j.ExtNodeInfo;
import it.m2j.GenNodeInfo;
import it.m2j.IdType;
import it.m2j.NodeInfo;
import it.m2j.Operator;
import it.m2j.SymbolDesc;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import ast.*;

/**
 * <p>Title: MiniC to Jasmin</p>
 * <p>Description: a MiniC to Jasmin Compiler developed for the "Progetto di Compilatori e interpreti" course at the Universit� degli studi di Catania</p>
 * <p>Website: http://code.google.com/p/minic-to-jasmin/ </p>
 * @author Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravida'
 * @version 1.0
 */

public class CodeGenerator extends Visitor
{
    private StringBuffer out = new StringBuffer();
    private SymbolTable sTable; //the top level symbol table
    
    private String jasminClassName;
    private int labelCounter;
    private int ifLabelCounter;
    private int ifRegister;
    private int whileCounter;
    
    private ArrayList<String> arrayNames;
    private int arrayCounter;

    /**
     * Creates a new Code Generation visitor object
     * @param st The global symbol table for the program
     * @param err a PrintWriter to output error messages to
     */
    public CodeGenerator( SymbolTable st, String className /* , CodeGenerator codeGen */)
    {
        sTable = st;
        jasminClassName = className;
        labelCounter = 0;
        ifLabelCounter = 0;
        arrayNames = new ArrayList<String>();
        arrayCounter = 0;
        ifRegister = 0;
        
        writeJasminHeader();
    }

    private void writeJasminHeader() {
    	writeStmt(".class public " + jasminClassName);
    	writeStmt(".super java/lang/Object");
    	writeStmt("");
    	writeStmt("; standard initializer");
    	writeStmt("");
    	writeStmt("");
    	writeStmt(".method public <init>()V");
    	writeStmt("aload 0");
    	writeStmt("invokenonvirtual java/lang/Object/<init>()V");
    	writeStmt("return");
    	writeStmt(".end method");
    	writeStmt("");
    	writeStmt("");
	}

	//write a statement to the output StringBuffer
    private void writeStmt(String stmt)
    {
        out.append("\t" + stmt);
        out.append("\n");
    }
    
    private void writeComment(String comment) {
    	out.append("; " + comment);
    	out.append("\n");
    }
    
    private void writeLabel() {
    	out.append("#" + labelCounter + ":");
    	out.append("\n");
    }
    
    private void writeLabel(String label) {
    	out.append(label + labelCounter + ":");
    	out.append("\n");
    }

    public String getOutput()
    {
        return out.toString();
    }

    public Object visit(ListNode node)
    {
        node.visitChildren(this);
        return new GenNodeInfo("", IdType.NULL, "", node.getType(), 0);
    }

    public Object visit(DeclNode node)
    {	
    	GenNodeInfo varInfo = (GenNodeInfo)node.visitVar(this);
    	GenNodeInfo initInfo;
    	IdType type = varInfo.getType();
    	String value = "";
    	
    	if (varInfo.getDim() == 0) {	//if it's a simple variable declaration (it's not an array) 
    	
	    	writeComment(type + " " + varInfo.getName() + " ----------------------------");
	    	
	    	switch(type)
	    	{
		    	case INT:
		    	case BOOL:
		    		value = "0";
		    		break;
		    		
		    	case FLOAT:
		    		value = "0.0";
		    		break;
		    		
		    	case STRING:
		    		value = "";
		    		break;
		    		
				default:
					break;
	    	}
	    	
	    	initInfo = new GenNodeInfo("", IdType.CONST, value, type, varInfo.getDim());
	    	
	    	//init variable
	    	pushInStack(node, initInfo);
	    	
	    	//load in local variable
	    	popFromStack(node, varInfo);
    	}
    	
    	//if it's an array, do nothing
    	else {
    		String name = varInfo.getName();
    		arrayNames.add(name);
    	}
    	
    	return null;
    }
    
    public Object visit(AssignNode node)
     {	
    	writeComment(node + " ----------------------------");
    	
    	GenNodeInfo left = (GenNodeInfo)node.visitVar(this);
    	GenNodeInfo right = (GenNodeInfo)node.visitValue(this);
    	
		// If the right hand is a constant or a variable
		if ((right.getKind() != IdType.NULL) && (right.getKind() != IdType.NEW)) {

    		
    		pushInStack(node, right);
    	}    	
		else if (left.getType() == IdType.FLOAT && right.getType() == IdType.INT) {
			writeStmt("i2f");
		}

		if (right.getKind() == IdType.NEW)
			writeStmt("astore "
					+ sTable.getVarDesc(left.getName(), node.getBlockNumber())
							.getJvmVar());
		else
			// pop from stack and store in local variable
			popFromStack(node, left);

		return null;
    }

    public Object visit(BlockNode node)
    {
    	node.visitChildren(this);
        
        return null;
    }

    public Object visit(FunctionNode node)
    {
    	visitFunction(node);

        return null;
    }

    private void visitFunction(FunctionNode node)
    {
    	IdType retType = node.getType();
    	String name = node.getName();
		int dim = node.getDimension();
		String brackets = "";
    	Node [] params;
    	
    	int numLocals = 0;
    	
    	//No params
    	if(node.getParams() == null)
		{			
			//check if it's an array to return the correct value type
			if (dim>=1) {
				for (int i = 0; i < dim; i++)
					brackets += "[";
			}
			writeStmt(".method public static " + name + "()" + brackets + getJVMType(retType));		
		}
    	//Params
    	else{
    		params = node.getParams().toArray();

            node.visitParams(this);

			//check if it's an array to return the correct value type
			if (dim>=1) {
				for (int i = 0; i < dim; i++)
					brackets += "[";
			}
			writeStmt(".method public static " + name + "("
					+ getParamTypes(params) + ")" + brackets + getJVMType(retType));

    	}
    	
        numLocals = sTable.varCount();
        
        ifRegister = numLocals;
        
        writeStmt(".limit locals " + numLocals*3);
        writeStmt(".limit stack " + numLocals);
        
        writeStmt("ldc 0");
        writeStmt("istore " + ifRegister);
        
        node.visitBody(this);
        
        writeStmt("\n.end method");
    }

    public Object visit(ArgNode node)
    {
    	IdType type = node.getType();
    	switch(type)
    	{
    		case INT:
    			//paramType = "I";
    			break;
    		
    		case FLOAT:
    			//paramType = "F";
    			break;
    		
    		case BOOL:
    			
    	}
    	
    	return null;
    }

    public Object visit(FunctionExtNode node)
    {
    	return null;
    }
    
    public Object visit(FuncCallNode node)
    {       	
    	String name = node.getName();
    	SymbolDesc fDesc = sTable.getFuncDesc(name);
    	IdType retType = fDesc.getType();
    	String className = fDesc.getClassName();
    	
    	//No params
    	if(node.getParams() == null)
            writeStmt("invokestatic " + className + "()" + getJVMType(retType));
    	
    	//Params
    	else
    	{    	
    		String s = "";
            
    		Object[] params = node.visitParams(this);
    		
    		for(int i=0;i<params.length;i++)
    		{
    			s += getJVMType(((GenNodeInfo)params[i]).getType());
    			pushInStack(node, (GenNodeInfo)params[i]);
    		}
    		
            writeStmt("invokestatic " + className +  "(" + s +
            		")" + getJVMType(retType));
    	}

        return new GenNodeInfo(name, IdType.NULL, "", retType, 0);
    }
 
    public Object visit(IfNode node)
    {
    	ifLabelCounter++;
    	
    	GenNodeInfo info = (GenNodeInfo) node.visitTest(this);
    	
    	if (info.getKind() == IdType.VARIABLE || info.getKind() == IdType.CONST)
    	{
    		pushInStack(node, info);
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		GenerateWriteBlock();
    	}
    	
    	//write exit block
    	writeLabel();
    	
    	writeStmt("iload " + ifRegister);
    	writeStmt("ifgt STMT_" + ifLabelCounter);
    	writeStmt("goto EXIT_" + ifLabelCounter);
    	
    	writeStmt("\rSTMT_" + ifLabelCounter + ":");
    	
    	int tempCounter = ifLabelCounter;
    	
    	node.visitThen(this);    	    	    	
    	
    	writeStmt("\rEXIT_" + tempCounter + ":"); // TODO: Fix with labelCreator class
    
    	labelCounter++;

        return null;
    }

    public Object visit(ReturnNode node)
    {
    	GenNodeInfo retInfo = (GenNodeInfo)node.visitValue(this);
    	
    	if(retInfo != null)
    		pushInStack(node, retInfo);
    	
    	if(retInfo.getDim() == 0) {

			switch (retInfo.getType()) {
			case INT:
			case BOOL:
				writeStmt("ireturn");
				break;
			case FLOAT:
				writeStmt("freturn");
				break;
			case STRING:
				writeStmt("areturn");
				break;
			default:
				writeStmt("return");
				break;
			}
		}
		else
			writeStmt("areturn");
		return null;
    }

    public Object visit(SubNode node)
    {
    	IdType type = visitBinaryNode(node, Operator.DIFF);
    	return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }

    public Object visit(DivNode node)
    {
    	IdType type = visitBinaryNode(node, Operator.DIV);
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }

    public Object visit(AddNode node)
    {
        IdType type = visitBinaryNode(node, Operator.PLUS);
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }
    
    public Object visit(MulNode node)
    {
    	IdType type = visitBinaryNode(node, Operator.MUL);
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }
    
	public Object visit(ModNode node) {
		IdType type = visitBinaryNode(node, Operator.MOD);
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
	}

    public Object visit(OrNode node)
    {
    	visitLogic(node, Operator.OR);    	    	    	    	
    	return new GenNodeInfo("", IdType.NULL, "", IdType.BOOL, 0);        
    }

    public Object visit(AndNode node)
    {       	
    	visitLogic(node, Operator.AND);
        return new GenNodeInfo("", IdType.NULL, "", IdType.BOOL, 0);
    }

    private void visitLogic(BinaryNode node, Operator op)
    {    	
    	//Left Hand
    	writeLabel();
    	labelCounter++;
    	
    	GenNodeInfo left = (GenNodeInfo) node.visitLeft(this);
    
    	if (left.getKind() == IdType.VARIABLE || left.getKind() == IdType.CONST)
    	{
    		pushInStack(node, left);
    		
    		//create a comparison with 0    		
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		GenerateWriteBlock();
    	}

    	if(op == Operator.AND)
    	{
        	writeLabel();
        	labelCounter++;

    		writeStmt("iload " + ifRegister);
    		writeStmt("ifle EXIT_" + ifLabelCounter);
    		writeStmt("goto #" + labelCounter);
    	}
    	
    	//Right Hand
    	writeLabel();
    	labelCounter++;

    	
    	GenNodeInfo right = (GenNodeInfo) node.visitRight(this);

    	if (right.getKind() == IdType.VARIABLE || right.getKind() == IdType.CONST)
    	{
    		pushInStack(node, right);
    		
    		//create a comparison with 0    		
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		GenerateWriteBlock();
    	}       	
    }
    
    private IdType visitBinaryNode(BinaryNode node, Operator op)
    {
    	String sOperator = "";
    	IdType retType;
    	
    	//Left Hand
    	GenNodeInfo left = (GenNodeInfo) node.visitLeft(this);
    	
    	//Right Hand
    	GenNodeInfo right = (GenNodeInfo) node.visitRight(this);    	

    	// if one or both operands are float
    	if(left.getType() == IdType.FLOAT || right.getType() == IdType.FLOAT) {

    		retType = IdType.FLOAT;
			sOperator = "f"; //float 
    		
    		if(left.getType() == IdType.INT) {
	        	//push in stack using iload or fload or ldc and check float type
	        	pushInStack(node, left);
	    		writeStmt("i2f");
	        	pushInStack(node, right);
    		}
    		
    		else if(right.getType() == IdType.INT) {
    			//push in stack using iload or fload or ldc and check float type
	        	pushInStack(node, left);
	        	pushInStack(node, right);
	        	writeStmt("i2f");
    		}
    		else {
    			pushInStack(node, left);
	        	pushInStack(node, right);
    		}
    	}    	
    	else {
    		
			retType = IdType.INT;
    		
			sOperator = "i"; //int
    		
    		pushInStack(node, left);
        	pushInStack(node, right);
    	}
    	
    	switch(op)
    	{
    		case PLUS:
    			
    			sOperator += "add";
    			writeStmt(sOperator);
    			break;
    			
    		case DIFF:
    			sOperator += "sub";
    			writeStmt(sOperator);
    			break;
    			
    		case MUL:
    			sOperator += "mul";
    			writeStmt(sOperator);
    			break;
    			
    		case DIV:
    			sOperator += "div";
    			writeStmt(sOperator);
    			break;
    		
    		case MOD:
    			sOperator += "rem";
    			writeStmt(sOperator);
    			break;
    			
    		case GT:
        			if(sOperator.equals("f")) {
        				sOperator = "fcmpg\n";
        				sOperator+= "\tifgt #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			else
        			{
        				sOperator = "if_icmpgt #"  + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			writeStmt(sOperator);
        			GenerateWriteBlock();
        			
        			break;
        			
        	case GET:
        			if(sOperator.equals("f")) {
        				sOperator = "fcmpg\n";
        				sOperator+= "\tifge #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			else
        			{
        				sOperator = "if_icmpge #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			writeStmt(sOperator);
        			GenerateWriteBlock();
        			
        			break;	
        		
        	case LT:
        			if(sOperator.equals("f")) {
        				sOperator = "fcmpl\n";
        				sOperator+= "\tiflt #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			else
        			{
        				sOperator = "if_icmplt #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			writeStmt(sOperator);
        			GenerateWriteBlock();
        			
        			break;
        			
        	case LET:
        			if(sOperator.equals("f")) {
        				sOperator = "fcmpl\n";
        				sOperator+= "\tifle #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			else
        			{
        				sOperator = "if_icmple #" + ++labelCounter;
        				sOperator+= "\n\tgoto #"+ ++labelCounter;
        			}
        			writeStmt(sOperator);
        			GenerateWriteBlock();

        			break;
        			
//        		case EQ:
//        			if(sOperator.equals("f")) {
//        				sOperator = "fcmpg\n";
//        				sOperator+= "ifgt " + "#" + labelCounter;
//        			}
//        			else
//        				sOperator = "if_icmpgt " + "#" + labelCounter;
//        			
//        			break;
//        			
//        		case NEQ:
//        			if(sOperator.equals("f")) {
//        				sOperator = "fcmpg\n";
//        				sOperator+= "ifgt " + "#" + labelCounter;
//        			}
//        			else
//        				sOperator = "if_icmpgt " + "#" + labelCounter;
//        			
//        			break;  
        		
        		default:
        			sOperator = "";
        			break;        			
    	}

    	//if (sOperator.length()>0)
    		//writeStmt(sOperator);
		
		return retType;
    }
        
	private void pushInStack(Node node, GenNodeInfo info) {
		SymbolDesc varDesc = null;

		String name = info.getName();
		IdType type = info.getType();
		String value = info.getValue();
		IdType kind = info.getKind();
		int dim = info.getDim();

		if (dim == 0) {

			if (kind == IdType.VARIABLE) {
				varDesc = sTable.getVarDesc(name, node.getBlockNumber());

				switch (type) {
				case BOOL:
				case INT:
					writeStmt("iload " + varDesc.getJvmVar());
					break;
				case FLOAT:
					writeStmt("fload " + varDesc.getJvmVar());
					break;
				case STRING:
					writeStmt("aload " + varDesc.getJvmVar());
					break;
				}
			} else if (kind == IdType.CONST) {
				switch (type) {
				case BOOL:
				case INT:
					int iValue = Integer.parseInt(value);

					if (iValue == -1) {
						writeStmt("iconst_m1");
					} else if (iValue >= 0 && iValue <= 5) {
						writeStmt("iconst_" + iValue);
					} else if (iValue >= -128 && iValue <= 127) {
						writeStmt("bipush " + iValue);
					} else if (iValue >= -32768 && iValue <= 32767) {
						writeStmt("sipush " + iValue);
					} else {
						writeStmt("ldc " + iValue);
					}

					break;

				case FLOAT:
					float fValue = Float.parseFloat(value);

					if (fValue == 0.0) {
						writeStmt("fconst_0");
					} else if (fValue == 1.0) {
						writeStmt("fconst_1");
					} else if (fValue == 2.0) {
						writeStmt("fconst_2");
					} else {
						writeStmt("ldc " + fValue);
					}

					break;

				case STRING:
					writeStmt("ldc " + "\"" + value + "\"");
					break;

				default:
					break;
				}
			}
		} else if (dim >= 1) {
			switch (type) {
			case BOOL:
				writeStmt("baload");
				break;
			case INT:
				writeStmt("iaload");
				break;
			case FLOAT:
				writeStmt("faload");
				break;
			case STRING:
				writeStmt("aaload");
				break;
			}
		}
	}

	private void popFromStack(Node node, GenNodeInfo info) {
		SymbolDesc varDesc = null;

		String name = info.getName();
		IdType type = info.getType();
		IdType kind = info.getKind();
		int dim = info.getDim();

		if (kind == IdType.VARIABLE) {
			varDesc = sTable.getVarDesc(name, node.getBlockNumber());

			// if it's a simple variable (not an array)
			if (dim == 0) {
				switch (type) {
				case BOOL:
				case INT:
					writeStmt("istore " + varDesc.getJvmVar());
					break;
				case FLOAT:
					writeStmt("fstore " + varDesc.getJvmVar());
					break;
				case STRING:
					writeStmt("astore " + varDesc.getJvmVar());
					break;
				}
			}
			// if it's an array
			else if (dim >= 1) {

				switch (type) {
				case BOOL:
					writeStmt("bastore");
					break;
				case INT:
					writeStmt("iastore");
					break;
				case FLOAT:
					writeStmt("fastore");
					break;
				case STRING:
					writeStmt("aastore");
					break;
				}
			}
		}
	}

  //*************************************** Comparison Nodes **********************************************
	
  	@Override
  	public Object visit(LTNode node) 
	{	
  		IdType type = visitBinaryNode(node, Operator.LT);
          
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);//TODO: FIxXx
  	}

  	public Object visit(LETNode node) {
  		IdType type = visitBinaryNode(node, Operator.LET);
        
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}

  	public Object visit(GTNode node) 
	{	
  		IdType type = visitBinaryNode(node, Operator.GT);
          
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}

  	public Object visit(GETNode node) {
  		IdType type = visitBinaryNode(node, Operator.GET);
        
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}
  	
  	public Object visit(EqNode node)
    {
		visitBinaryNode(node, Operator.EQ);
        
		return null;
    }

  	public Object visit(NotEqNode node)
  	{
  		visitBinaryNode(node, Operator.NEQ);

  		return null;
  	}
	
  	public Object visit(NotNode node)
  	{
  		//          node.visitChild(this);
  		//
  		//          //push true onto the stack and xor the top two values
  		//          //effectivly negating the previous top of the stack (if it was 0 or 1)
  		//          writeStmt("iconst_1");
  		//          writeStmt("ixor");

  		return null;
  	}
      
  //********************************************************************************************************
    
    public Object visit(StringNode node)
    {	
        GenNodeInfo info = new GenNodeInfo("",IdType.CONST, node.toString(), node.getType(), 0);
        return info;
    }

    public Object visit(BoolNode node)
    {
    	String sRet = "";
    	
    	if(node.toString() == "true")
    		sRet = "1";
    	else
    		sRet = "0";
    	
        GenNodeInfo info = new GenNodeInfo("",IdType.CONST, sRet, node.getType(), 0);
        return info;
    }

    public Object visit(IntNode node)
    {
        GenNodeInfo info = new GenNodeInfo("",IdType.CONST, node.toString(), node.getType(), 0);
        return info;
    }

	public Object visit(FloatNode node) 
	{
		GenNodeInfo info = new GenNodeInfo("",IdType.CONST, node.toString(), node.getType(), 0);
        return info;
	}
	@Override
	public Object visit(IfElseNode node) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object visit(CastNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(SignNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(WhileNode node) 
	{
		ifLabelCounter++;
		
    	writeStmt("\rWHILE_" + ifLabelCounter + ":");
    	
    	GenNodeInfo info = (GenNodeInfo) node.visitTest(this);
    	
    	if (info.getKind() == IdType.VARIABLE || info.getKind() == IdType.CONST)
    	{
    		pushInStack(node, info);
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		GenerateWriteBlock();
    	}
    	
    	//write exit block
    	writeLabel();
    	
    	writeStmt("iload " + ifRegister);
    	writeStmt("ifgt STMT_" + ifLabelCounter);
    	writeStmt("goto EXIT_" + ifLabelCounter);
    	
    	writeStmt("\rSTMT_" + ifLabelCounter + ":");
    	
    	int tempCounter = ifLabelCounter;
    	
    	node.visitWhile(this);    	    	    	
    	
    	writeStmt("ldc 0");
        writeStmt("istore " + ifRegister);
    	writeStmt("goto WHILE_" + tempCounter);
    	
    	writeStmt("\rEXIT_" + tempCounter + ":"); // TODO: Fix with labelCreator class
    
    	labelCounter++;

        return null;
	}

	@Override
	public Object visit(NullNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}
	
    private String getParamTypes(Node[] nodes)
    {
    	String sRet = "";
    	
    	for(int i=0;i<nodes.length;i++)
    		sRet += getJVMType(nodes[i].getType());
    	
    	return sRet;
    }
    
    private String getJVMType(IdType t)
    {
    	switch(t)
		{
			case INT:
				return "I";
			case FLOAT:
				return "F";
			case STRING:
				return "Ljava/lang/String;";
			case BOOL:
				return "I";
			case VOID:
				return "V";
			default:
				return "V";
		}
    }

    private void GenerateWriteBlock()
    {
    	labelCounter--;
    	writeLabel();
    	labelCounter++;

    	writeStmt("iconst_1");
    	writeStmt("istore " + ifRegister);
    }
    
	public Object visit(ArrayNewNode node) {
		
		String name = arrayNames.get(arrayCounter++);
		IdType type = node.getType();
		int dim = node.getDimension();
		GenNodeInfo info = (GenNodeInfo) node.visitDim(this); //dovrebbe tornare la size dell'array, il valore da settare sotto
		
		if (dim == 1) {
			// we use anewarray for string type
			if (type == IdType.STRING)
				writeStmt("anewarray " + "java/lang/String");
			else if (type == IdType.BOOL)
				writeStmt("newarray boolean");
			else
				// we use newarray for types int, float and bool
				writeStmt("newarray " + type);
		} else if (dim > 1) {
			String brackets = "";
			for (int i = 0; i < dim; i++)
				brackets += "[";
			// we use multianewarray for n-dimension array
			writeStmt("multianewarray " + brackets + getJVMType(type) + " "
					+ dim);
		}
		return new GenNodeInfo(name, IdType.NEW, "", type, dim);

	}

	public Object visit(ArrayCallNode node) {
		SymbolDesc arrayDesc = sTable.getVarDesc(node.getName(),
				node.getBlockNumber());
		writeStmt("aload " + arrayDesc.getJvmVar());

		GenNodeInfo infoVar = (GenNodeInfo) node.visitVar(this);
		GenNodeInfo infoDim = (GenNodeInfo) node.visitDim(this);

		return new GenNodeInfo(infoVar.getName(), IdType.VARIABLE, "",
				infoVar.getType(), infoVar.getDim());
	}

	public Object visit(SimpleVarNode node) 
	{
		SymbolDesc varDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());
		
		GenNodeInfo info = new GenNodeInfo(node.getName(), IdType.VARIABLE, "", varDesc.getType(), varDesc.getDim());
		
		return info;
	}
	
	public Object visit(ArrayNode node) {
		
		SymbolDesc arrayDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());
		
		GenNodeInfo info = new GenNodeInfo(node.getName(), IdType.VARIABLE, "", arrayDesc.getType(), arrayDesc.getDim());
		
		return info;
	}

	public Object visit(ArraySizeNode node) {
		
		GenNodeInfo info = (GenNodeInfo) node.visitExpr(this);
		pushInStack(node, info);
		return info;
	}
}