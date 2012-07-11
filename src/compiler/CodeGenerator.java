package compiler;

import it.m2j.GenNodeInfo;
import it.m2j.IdType;
import it.m2j.NodeInfo;
import it.m2j.Operator;
import it.m2j.SymbolDesc;

import java.io.FileWriter;
import java.io.PrintWriter;

import ast.*;

/**
 * <p>Title: MiniC to Jasmin</p>
 * <p>Description: a MiniC to Jasmin Compiler developed for the "Progetto di Compilatori e interpreti" course at the Universitˆ degli studi di Catania</p>
 * <p>Website: http://code.google.com/p/minic-to-jasmin/ </p>
 * @author Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravidˆ
 * @version 1.0
 */

public class CodeGenerator extends Visitor
{
    private StringBuffer out = new StringBuffer();
    private SymbolTable sTable; //the top level symbol table
    
    private String jasminClassName;    

    /**
     * Creates a new Code Generation visitor object
     * @param st The global symbol table for the program
     * @param err a PrintWriter to output error messages to
     */
    public CodeGenerator( SymbolTable st, PrintWriter err, String className /* , CodeGenerator codeGen */)
    {
        super(err);

        sTable = st;
        jasminClassName = className;
        
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
        out.append(stmt);
        out.append("\n");
    }

    public String getOutput()
    {
        return out.toString();
    }

    public Object visit(ListNode node)
    {
        node.visitChildren(this);
        
        return null;
    }

    public Object visit(DeclNode node)
    {	
    	GenNodeInfo varInfo = (GenNodeInfo)node.visitVar(this);
    	GenNodeInfo initInfo;
    	IdType type = varInfo.getType();
    	String value = "";
    	
    	writeStmt("; " + type + " " + varInfo.getName() + " ----------------------------");
    	
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
    	
    	initInfo = new GenNodeInfo("", value, type, varInfo.getDim());
    	
    	//init variable
    	pushInStack(node, initInfo);
    	
    	//load in local variable
    	popFromStack(node, varInfo);
    	
    	return null;
    }
    
    public Object visit(AssignNode node)
    {	
    	writeStmt("; " + node + " ----------------------------");
    	
    	GenNodeInfo left = (GenNodeInfo)node.visitVar(this);
    	GenNodeInfo right = (GenNodeInfo)node.visitValue(this);
    	
    	if(right != null)
    		pushInStack(node, right);

    	//pop from stack and store in my local variable
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
    	Node [] params;
    	
    	int numLocals = 0;
    	
    	//No params
    	if(node.getParams() == null)
            writeStmt(".method public static " + name + "()" + getJVMType(retType));
    	//Params
    	else{
    		params = node.getParams().toArray();
    		
            node.visitParams(this);

            writeStmt(".method public static " + name + "(" + getParamTypes(node.getParams().toArray()) +
            		")" + getJVMType(retType));
    	}
    	
        numLocals = sTable.varCount();
        
        writeStmt(".limit locals " + numLocals);
        writeStmt(".limit stack " + numLocals);
        
        node.visitBody(this);
        
        writeStmt(".end method");
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

    public Object visit(FuncCallNode node)
    {
//        boolean oldExpr = inExpr;
//        inExpr = true;
//        MethodSymbol method = node.getSymbol();
//
//        writeStmt(";" + node.toString());
//
//        //save register 5
//        CodeGenerator.pushVar(5, out);
//
//        //find the target and store the target in a register
//        node.visitTarget(this);
//        writeStmt("istore 5");
//
//        int retAdd = pushPreMethodCall(node, 5);
//        inExpr = oldExpr;
//
//        //figure out the method number
//        writeStmt("aload 1"); //push the heap
//        writeStmt("iload 5");  //push the target
//        writeStmt("iaload");  //get the target's dynamic class number
//        writeStmt("aload 1");
//        writeStmt("swap");
//        writeStmt("iaload"); //get the pointer to the class descriptor
//        writeStmt("ldc " + (method.getOwner().getMethodOffset(method.getMethodNumber()) + 2)); //push an offset for the method
//        writeStmt("iadd"); //now have the address of the method's number on top of the stack
//        writeStmt("aload 1");
//        writeStmt("swap");
//        writeStmt("iaload");  //now have the method number
//
//        //use the jump table
//        writeStmt("goto jumpTable");
//
//        //the return label
//        writeStmt(NumberGenerator.getInstance().makeRetAdd(retAdd) + ":");
//
//        //pop the result (if its void we can ignore it)
//        CodeGenerator.popToStack(out);
//        writeStmt("istore 5");
//
//        //restore the local variables and 'this'
//        restoreVariables();
//
//        //push the result (if the method is not void)
//        if (!method.getType().equals("void") && inExpr)
//            writeStmt("iload 5");
//
//        //restore register 5
//        CodeGenerator.popToVar(5, out);

        return null;
    }

    private int pushPreMethodCall(InvocNode node, int target)
    {
//        //save 'this' and local variables on to the stack
//        saveVariables();
//
//        //push the return address
//        int retAdd = NumberGenerator.getInstance().getRetAddress();
//        CodeGenerator.pushConst(retAdd, out);
//
//        //push the target, to become 'this'
//        CodeGenerator.pushVar(target, out);
//        //push the params in l --> r order
//        Node[] params = node.getParams();
//        for (int i = 0; i < params.length; ++i)
//        {
//            params[i].accept(this);
//            CodeGenerator.pushFromStack(out);
//        }
//
//        return retAdd;
    	
    	return 0;
    }

    //saves variables and this on the stack before a method call
    private void saveVariables()
    {
//        writeStmt(";save variables");
//        CodeGenerator.pushVar(0, out);
//        for (int i = 11; i < NumberGenerator.getInstance().getTotalLocals(); ++i)
//            CodeGenerator.pushVar(i, out);
    }

    //restores variables and this from the stack after a method call
    private void restoreVariables()
    {
//        writeStmt(";restore variables");
//        for (int i = NumberGenerator.getInstance().getTotalLocals()-1; i >= 11; --i)
//        {
//            CodeGenerator.popToStack(out);
//            writeStmt("istore " + i);
//        }
//        CodeGenerator.popToStack(out);
//        writeStmt("istore 0");
    }

    public Object visit(IfNode node)
    {
//        boolean oldExpr = inExpr;
//        inExpr = true;
//
//        node.visitTest(this); //put the result of the test on the stack
//
//        inExpr = oldExpr;
//
//        String endLabel = NumberGenerator.getInstance().getLabel();
//
//        writeStmt("ifeq " + endLabel); //jump if false-+
//        node.visitThen(this); //execute the then part  |
//        writeStmt(endLabel + ":");    // <-------------+

        return null;
    }

    public Object visit(ReturnNode node)
    {
    	GenNodeInfo retInfo = (GenNodeInfo)node.visitValue(this);
    	
    	if(retInfo != null)
    		pushInStack(node, retInfo);
    	
    	switch(retInfo.getType())
    	{
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
    	
    	return null;      
    }

    public Object visit(PrintNode node)
    {
//        boolean oldExpr = inExpr;
//        inExpr = true;
//
//        writeStmt(";" + node.toString());
//        node.visitValue(this);
//
//        //we have either an int, a boolean or an Object* on the stack, and we need java.lang.String*
//        unknownToString(node.getType());
//
//        writeStmt("getstatic java/lang/System/out Ljava/io/PrintStream;");
//        writeStmt("swap");
//        writeStmt("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V");
//
//        inExpr = oldExpr;
        
        return null;
    }

    //we have either an int, a boolean or an Object pointer on top of the stack
    //and we want a java.lang.String pointer
    private void unknownToString(String type)
    {
//        if (type.equals("int"))
//        {
//            writeStmt(";converting int to String*");
//            writeStmt("new java/lang/StringBuffer");
//            writeStmt("dup"); //duplicate it
//            writeStmt("invokenonvirtual java/lang/StringBuffer/<init>()V"); //call its constructor
//            writeStmt("swap");
//            writeStmt("invokevirtual java/lang/StringBuffer/append(I)Ljava/lang/StringBuffer;");
//            writeStmt("invokevirtual java/lang/StringBuffer/toString()Ljava/lang/String;");
//        }
//        else if (type.equals("boolean"))
//        {
//            writeStmt(";converting boolean to String*");
//            String notZeroLabel = NumberGenerator.getInstance().getLabel();
//            String endLabel = NumberGenerator.getInstance().getLabel();
//            writeStmt("ifeq " + notZeroLabel); //jump if 0 ---------+
//            writeStmt("ldc \"true\""); //not 0 so push true         |
//            writeStmt("goto " + endLabel);  //jump to end label ----+--+
//            writeStmt(notZeroLabel + ":");  //  <-------------------+  |
//            writeStmt("ldc \"false\""); //    0 so push false          |
//            writeStmt(endLabel + ":");    //    <----------------------+
//        }
//        else  //Object*
//        {
//            writeStmt(";converting Object* to String*");
//
//            saveVariables();
//
//            //execute Object.toString to get a String on top of the stack
//            int retAdd = NumberGenerator.getInstance().getRetAddress();
//            CodeGenerator.pushConst(retAdd, out);
//
//            //copy and push the target pointer
//            writeStmt("dup");
//            CodeGenerator.pushFromStack(out);
//
//            //method dispatch
//            writeStmt("aload 1");
//            writeStmt("swap");
//            writeStmt("iaload"); //class number now on top of the stack
//            writeStmt("aload 1");
//            writeStmt("swap");
//            writeStmt("iaload"); //get the pointer to the class descriptor
//            int offset = ((ClassSymbol)sTable.get("Object")).getMethodOffset(1) + 2;
//            writeStmt("ldc " + offset);
//            writeStmt("iadd");
//            writeStmt("aload 1");
//            writeStmt("swap");
//            writeStmt("iaload");  //now have the method number on top of the stack
//
//            writeStmt("goto jumpTable");
//            writeStmt(NumberGenerator.getInstance().makeRetAdd(retAdd) + ":");
//
//            CodeGenerator.popToStack(out);
//            //we now have a String* on top of the stack
//
//            restoreVariables();
//
//            //get the java.lang.String out of the String
//            getjavaString();
//        }
    }

    //code to extract a java.lang.String pointer from a String pointer
    //expects a String* to be on top of the stack
    private void getjavaString()
    {
//        writeStmt(";converting String* to Ljava.lang.String");
//        writeStmt("iconst_2");  //we know the field offset is 2
//        writeStmt("iadd");  //field address is now on the stack
//        writeStmt("aload 1");
//        writeStmt("swap");
//        writeStmt("iaload");   //get the string pool pointer
//        writeStmt("aload 3");
//        writeStmt("swap");
//        writeStmt("aaload"); //now have a java.lang.String* on top of the stack
    }

    //expects a Ljava/lang/String on top of the stack and leaves a string pool index on the stack
    private void addToStringPool()
    {
        writeStmt(";addToStringPool()");
        writeStmt("aload 3");
        writeStmt("swap");
        writeStmt("iload 4");
        writeStmt("swap");
        writeStmt("aastore");
        writeStmt("iload 4");
        writeStmt("iinc 4 1");
    }

    public Object visit(SubNode node)
    {
        visitBinaryNode(node, Operator.DIFF);
        return null;
    }

    public Object visit(DivNode node)
    {
        visitBinaryNode(node, Operator.DIV);
        return null;
    }

    public Object visit(AddNode node)
    {
        visitBinaryNode(node, Operator.PLUS);
        return null;
    }
    
    public Object visit(MulNode node)
    {
        visitBinaryNode(node, Operator.MUL);
        return null;
    }

    public Object visit(OrNode node)
    {
        visitBinaryNode(node, Operator.OR);
        return null;
    }

    public Object visit(AndNode node)
    {
        visitBinaryNode(node, Operator.AND);
        return null;
    }

    private void visitBinaryNode(BinaryNode node, Operator op)
    {
    	//Left Hand
    	GenNodeInfo left = (GenNodeInfo) node.visitLeft(this);
    	
    	//Right Hand
    	GenNodeInfo right = (GenNodeInfo) node.visitRight(this);

    	//push in stack using iload or fload or ldc and check float type
    	pushInStack(node, left);
    	pushInStack(node, right);
    	
    	switch(op)
    	{
    		case PLUS:
    			if(left.getType() == IdType.FLOAT || right.getType() == IdType.FLOAT)
    				writeStmt("fadd");
    			else 
    				writeStmt("iadd");
    			break;
    			
    		case DIFF:
    			
    			break;
    			
    		case MUL:
    			
    			break;
    			
    		case DIV:
    			
    			break;
    			
    		default:
    			break;
    	}
    }
    
    private void pushInStack(Node node, GenNodeInfo info) 
    {
    	SymbolDesc varDesc = null;
    	
    	String name = info.getName();
    	IdType type = info.getType();
    	String value = info.getValue();
    	
    	/*
    	 * If it has name, it is variable.
    	 * If name is empty, it is number.
    	 */
    	if (!name.equals(""))
    	{
    		varDesc = sTable.getVarDesc(name, node.getBlockNumber());
    		
    		switch(type)
    		{
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
    	}
    	else
    		{
    			switch(type)
    			{
    				case BOOL:
    				case INT:
    					int iValue = Integer.parseInt(value);
    					
    				      if(iValue == -1) {
    				          writeStmt("iconst_m1");
    				       } else if(iValue >= 0 && iValue <= 5) {
    				    	  writeStmt("iconst_" + iValue);
    				       } else if(iValue >= -128 && iValue <= 127) {
    				          writeStmt("bipush " + iValue);
    				       } else if(iValue >= -32768 && iValue <= 32767) {
    				          writeStmt("sipush " + iValue);
    				       } else {
    				          writeStmt("ldc " + iValue);
    				       }
    				      
    					break;
    					
    				case FLOAT:
    					float fValue = Float.parseFloat(value);
    					
    				      if(fValue == 0.0) {
    				          writeStmt("fconst_0");
    				       } else if(fValue == 1.0) {
    				    	   writeStmt("fconst_1");
    				       } else if(fValue == 2.0) {
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
	}

    private void popFromStack(Node node, GenNodeInfo info) 
    {
    	SymbolDesc varDesc = null;
    	
    	String name = info.getName();
    	IdType type = info.getType();
    	
    	/*
    	 * If it has name, it is variable.
    	 * If name is empty, it is number.
    	 */
    	if (!name.equals(""))
    	{
    		varDesc = sTable.getVarDesc(name, node.getBlockNumber());
    		
    		switch(type)
    		{
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
	}
    
    public Object visit(EqNode node)
    {
//        writeStmt(";" + node.toString());
//
//        //save register 5
//        CodeGenerator.pushVar(5, out);
//
//        //visit the left and right nodes and push them onto the stack
//        node.visitLeft(this);
//        writeStmt("istore 5");
//        node.visitRight(this);
//        writeStmt("iload 5");
//        writeStmt("swap");
//
//        String notEqLabel = NumberGenerator.getInstance().getLabel();
//        String endLabel = NumberGenerator.getInstance().getLabel();
//        writeStmt("if_icmpne " + notEqLabel); //jump if not equal
//        writeStmt("iconst_1"); //equal so push 1            //  |
//        writeStmt("goto " + endLabel);  //jump to end label ----+--+
//        writeStmt(notEqLabel + ":");  //    <-------------------+  |
//        writeStmt("iconst_0"); //not equal so push 0               |
//        writeStmt(endLabel + ":");    //    <----------------------+
//
//        //restore register 5
//        CodeGenerator.popToVar(5, out);

        return null;
    }

    public Object visit(NotEqNode node)
    {
//        writeStmt(";" + node.toString());
//
//        //save register 5
//        CodeGenerator.pushVar(5, out);
//
//        //visit the left and right nodes and push them onto the stack
//        node.visitLeft(this);
//        writeStmt("istore 5");
//        node.visitRight(this);
//        writeStmt("iload 5");
//        writeStmt("swap");
//
//        String eqLabel = NumberGenerator.getInstance().getLabel();
//        String endLabel = NumberGenerator.getInstance().getLabel();
//        writeStmt("if_icmpeq " + eqLabel);          //jump if equal
//        writeStmt("iconst_1"); //equal so push 1            //  |
//        writeStmt("goto " + endLabel);  //jump to end label ----+--+
//        writeStmt(eqLabel + ":");     //    <-------------------+  |
//        writeStmt("iconst_0"); //not equal so push 0               |
//        writeStmt(endLabel + ":");    //    <----------------------+
//
//        //restore register 5
//        CodeGenerator.popToVar(5, out);

        return null;
    }

    public Object visit(NegNode node)
    {
//        node.visitChild(this);
//
//        //negate the number on top of the stack
//        writeStmt("ineg");
        
        return null;
    }

    public Object visit(NotNode node)
    {
//        node.visitChild(this);
//
//        //push true onto the stack and xor the top two values
//        //effectivly negating the previous top of the stack (if it was 0 or 1)
//        writeStmt("iconst_1");
//        writeStmt("ixor");
        
        return null;
    }

    public Object visit(StringNode node)
    {	
        GenNodeInfo info = new GenNodeInfo("", node.toString(), node.getType(), 0);
        return info;
    }

    public Object visit(BoolNode node)
    {
    	String sRet = "";
    	
    	if(node.toString() == "true")
    		sRet = "1";
    	else
    		sRet = "0";
    	
        GenNodeInfo info = new GenNodeInfo("", sRet, node.getType(), 0);
        return info;
    }

    public Object visit(IntNode node)
    {
        GenNodeInfo info = new GenNodeInfo("", node.toString(), node.getType(), 0);
        return info;
    }

	public Object visit(FloatNode node) 
	{
		GenNodeInfo info = new GenNodeInfo("", node.toString(), node.getType(), 0);
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

	@Override
	public Object visit(ModNode modNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LTNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(LETNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(GTNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(GETNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(SignNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(WhileNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(NullNode letNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ArrayNode letNode) {
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

	@Override
	public Object visit(ArrayNewNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ArrayCallNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visit(SimpleVarNode node) 
	{
		SymbolDesc varDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());
		
		GenNodeInfo info = new GenNodeInfo(node.getName(), node.toString(), varDesc.getType(), varDesc.getDim());
		
		return info;
	}

	@Override
	public Object visit(ArraySizeNode node) {
		// TODO Auto-generated method stub
		return null;
	}
}