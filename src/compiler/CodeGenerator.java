package compiler;

import aux.*;
import java.util.ArrayList;

import utils.StuffCreator;

import ast.*;

/**
 * <p>Title: MiniC to Jasmin</p>
 * <p>Description: a MiniC to Jasmin Compiler developed for the "Progetto di Compilatori e interpreti" course at the Universita' degli studi di Catania</p>
 * <p>Website: http://code.google.com/p/minic-to-jasmin/ </p>
 * @author Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravida'
 * @version 1.0
 */

public class CodeGenerator extends Visitor
{
    private StringBuffer out = new StringBuffer();
    private SymbolTable sTable; //the top level symbol table
    
    private boolean returned;
    
    private String jasminClassName;
    private int labelCounter;
    private int ifLabelCounter;
    private int auxRegister;    
    
    private ArrayList<String> arrayNames;
    private int arrayCounter;
    private int multiArrayDim; //global variable to store the dimension of array n-dimension

    /**
     * Creates a new Code Generation visitor object
     * @param st The global symbol table for the program
     * @param className The name of current class file
     */
    public CodeGenerator( SymbolTable st, String className)
    {
        sTable = st;
        jasminClassName = className;
        labelCounter = 0;
        ifLabelCounter = 0;
        arrayNames = new ArrayList<String>();
        multiArrayDim = 0;
        arrayCounter = 0;
        auxRegister = 0;
        
        writeJasminHeader();
        returned = false;
    }

    /**
     * Create header of Jasmin output file
     */
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

    /**
     * Write a statement to the output StringBuffer
     */
    private void writeStmt(String stmt)
    {
        out.append("\t" + stmt);
        out.append("\n");
    }
    
    /**
     * Write a comment to the output StringBuffer
     */
    private void writeComment(String comment) {
    	out.append("; " + comment);
    	out.append("\n");
    }
    
    /**
     * Write a label to the output StringBuffer
     */
    private void writeLabel() {
    	out.append("#" + labelCounter + ":");
    	out.append("\n");
    }

    /**
     * Get output buffer off
     */
    public String getOutput()
    {
        return out.toString();
    }

    /**
     * Visit the ListNode of the AST. Return a GenNodeInfo object with node type.
     * The visitChildren method, throw the 'accept' method by left and right child.
     * @param node The ListNode reference
     */
    public Object visit(ListNode node)
    {
        node.visitChildren(this);
        
        return new GenNodeInfo("", IdType.NULL, "", node.getType(), 0);
    }

    /**
     * Visit the DeclNode of the AST. It's used for instructions like 'int a;'
     * @param node The DeclNode reference
     */
    public Object visit(DeclNode node)
    {	
    	// visit VarNode
    	GenNodeInfo varInfo = (GenNodeInfo)node.visitVar(this);
    	
    	IdType type = varInfo.getType();
    	String value = "";
    	
    	GenNodeInfo initInfo;
    	
    	//if it's a simple variable declaration (it's not an array)
    	if (varInfo.getDim() == 0) { 
    	
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
	    	
	    	//create GenNodeInfo object with CONSTANT kind
	    	initInfo = new GenNodeInfo("", IdType.CONST, value, type, varInfo.getDim());
	    	
	    	//Initialize variable using push/pop operations in and from the stack  
	    	pushInStack(node, initInfo);
	    	popFromStack(node, varInfo);
    	}
    	
    	//if it's an array, do nothing
    	else {
    		String name = varInfo.getName();
    		arrayNames.add(name);
    	}
    	
    	return null;
    }
    
    /**
     * Visit the AssignNode of the AST. It's used for instructions like 'x = y;'
     * @param node The AssignNode reference
     */
    public Object visit(AssignNode node)
    {	
    	writeComment(node + " ----------------------------");
    	
    	// Visit LEFT hand of assignment
    	GenNodeInfo left = (GenNodeInfo)node.visitVar(this);
    	
    	// If it is an array
    	if(left.getKind() == IdType.ARRAYCALLNODE)
    		visitArrayCallNode(left);

    	// Visit RIGHT hand of assignment
    	GenNodeInfo right = (GenNodeInfo)node.visitValue(this);
    	
    	// If it is an array
    	if(right.getKind() == IdType.ARRAYCALLNODE)
    		visitArrayCallNode(right);
    	    	
		// If the right hand is a constant or a variable
		if ((right.getKind() != IdType.NULL) && (right.getKind() != IdType.NEW))    		
    		pushInStack(node, right);
		
		// FLOAT = INT --> need convert integer to float
		if (left.getType() == IdType.FLOAT && right.getType() == IdType.INT)
			writeStmt("i2f");

		// Allocate new array
		if (right.getKind() == IdType.NEW)
			writeStmt("astore "
					+ sTable.getVarDesc(left.getName(), node.getBlockNumber())
							.getJvmVar());
		else
			// pop from stack and store in local variable
			popFromStack(node, left);

		return null;
    }

    /**
     * Visit the BlockNode of the AST. 
     * It's call 'accept' method by his list of statements 
     * @param node The BlockNode reference
     */
    public Object visit(BlockNode node)
    {
    	node.visitChildren(this);
        
        return null;
    }

    /**
     * Visit the FunciontNode of the AST.
     * @param node The Function node reference
     */
    public Object visit(FunctionNode node)
    {
    	// exploring the function block
    	visitFunction(node);

        return null;
    }

    /**
     * Visit params and body of current FunctionNode
     * @param node The Function node reference
     */
    private void visitFunction(FunctionNode node)
    {
    	Node [] params;
    	
    	IdType retType = node.getType();
    	String name = node.getName();
		int dim = node.getDimension();    	
    	
		int numLocals = 0;
    	
    	// If no params, write assembler code to declare method
    	if(node.getParams() == null)						
			writeStmt(".method public static " + name + "()" + StuffCreator.getOpenBrackets(dim) + StuffCreator.getJVMType(retType));
    	
    	// If params, visit them and check type and array dimension
    	else{
    		params = node.getParams().toArray();

            node.visitParams(this);

			writeStmt(".method public static " + name + "("
					+ getParamTypes(params) + ")" + StuffCreator.getOpenBrackets(dim) + StuffCreator.getJVMType(retType));
    	}
    	
    	// Get number of all variable
        numLocals = sTable.varCount();
        
        // Set JVM name for the auxiliary register
        auxRegister = numLocals;

        // Set limits for stack and local variables
        // We multiply them for 3 to fix stack size issue 
        writeStmt(".limit locals " + numLocals*3);
        writeStmt(".limit stack " + numLocals*3);

        //Reset the auxRegister
    	writeStmt("ldc 0");
    	writeStmt("istore " + auxRegister);

    	// Visit function body
        node.visitBody(this);

        // If function is VOID
        if(!returned)
        	writeStmt("return");

        writeStmt("\n.end method");
    }

    /**
     * Visit ArgNode of the AST. Do nothing
     * @param node The ArgNode reference
     */
    public Object visit(ArgNode node)
    {    	
    	return null;
    }

    /**
     * Visit FunctionExtNode of the AST. Do nothing
     * @param node The FunctionExtNode reference
     */
    public Object visit(FunctionExtNode node)
    {
    	return null;
    }
    
    /**
     * Visit FuncCallNode of the AST.
     * It's used for calls methods.
     * @param node The FunCallNode reference
     */
    public Object visit(FuncCallNode node)
    {       	
    	// Take function name
    	String name = node.getName();
    	
    	// Take function descriptor in the symbol table
    	SymbolDesc fDesc = sTable.getFuncDesc(name);
    	IdType retType = fDesc.getType();
    	String className = fDesc.getClassName();
    	
    	// If no params
    	if(node.getParams() == null)
            writeStmt("invokestatic " + className + "()" + StuffCreator.getJVMType(retType));    	

    	// If params, visit them
    	else
    	{    	
    		String s = "";
            
    		Object[] params = node.visitParams(this);
    		
    		for(int i=0;i<params.length;i++)
    		{
    			// Return dimension ( '[' char for any dimension )
    			s += StuffCreator.getOpenBrackets(((GenNodeInfo)params[i]).getDim());
    			// Return type
    			s += StuffCreator.getJVMType(((GenNodeInfo)params[i]).getType());
    			
    			// Push params in stack
    			pushInStack(node, (GenNodeInfo)params[i]);
    		}
    		
            writeStmt("invokestatic " + className +  "(" + s +
            		")" + StuffCreator.getJVMType(retType));
    	}

        return new GenNodeInfo(name, IdType.NULL, "", retType, 0);
    }

    /**
     * Visit IfNode of the AST.
     * @param node The IfNode reference
     */
    public Object visit(IfNode node)
    {
    	// Increase the label counter
    	ifLabelCounter++;
    	
    	//Reset register for Exit Condition
    	setAuxRegister(0);	
        
    	// Visit IF condition
    	GenNodeInfo info = (GenNodeInfo) node.visitTest(this);
    	
    	// If condition is a variable or a constant
    	if (info.getKind() == IdType.VARIABLE || info.getKind() == IdType.CONST)
    	{
    		pushInStack(node, info);
    		
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		// Write exit condition
    		writeTrue();
    	}
    	
    	//write exit block
    	writeLabel();    	
    	
		// Push true onto the stack and XOR the top two values
		// effectivly negating the previous top of the stack (if it was 0 or 1)
    	if (info.getKind() == IdType.NOTNODE)
    	{
    		writeStmt("iload " + auxRegister);
    		writeStmt("iconst_1");
    		writeStmt("ixor");
    		writeStmt("istore " + auxRegister);
    	}    	
    	
    	// Load auxiliary register and check the exit condition
    	writeStmt("iload " + auxRegister);
    	writeStmt("ifgt STMT_" + ifLabelCounter);
    	writeStmt("goto EXIT_" + ifLabelCounter);
    	
    	writeStmt("\rSTMT_" + ifLabelCounter + ":");
    	
    	// Freeze ifLabelCounter register value.
    	// This fix issues in other use of this register
    	int tempCounter = ifLabelCounter;
    	
    	// Visit IF body
    	node.visitThen(this); 
    	
		//Reset register for Exit Condition
    	setAuxRegister(0);
    	
    	writeStmt("\rEXIT_" + tempCounter + ":");
    	writeStmt("nop");
    
    	labelCounter++;

        return null;
    }

    /**
     * Visit IfElseNode of the AST.
     * @param node The IfElseNode reference
     */
	public Object visit(IfElseNode node) 
	{
		// Increase the label counter
		ifLabelCounter++;
    	
    	// Reset register for Exit Condition
		setAuxRegister(0);
    	
		// Visit IF condition
    	GenNodeInfo info = (GenNodeInfo) node.visitTest(this);
    	
    	// If condition is a variable or a constant
    	if (info.getKind() == IdType.VARIABLE || info.getKind() == IdType.CONST)
    	{
    		pushInStack(node, info);
    		
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		// Write exit condition
    		writeTrue();
    	}
    	
    	//write exit block
    	writeLabel();
    	
    	// Load auxiliary register and check the exit condition
    	writeStmt("iload " + auxRegister);
    	writeStmt("ifgt STMT_" + ifLabelCounter);
    	writeStmt("goto ELSE_" + ifLabelCounter);
    	
    	writeStmt("\rSTMT_" + ifLabelCounter + ":");

    	// Freeze ifLabelCounter register value.
    	// This fix issues in other use of this register
    	int tempCounter = ifLabelCounter;
    	
    	// Visit IF body
    	node.visitThen(this); 
    	
    	writeStmt("\rgoto EXIT_" + tempCounter);
    	writeStmt("\rELSE_" + ifLabelCounter + ":");
    	
    	// Visit Else body
    	node.visitElse(this);
    	
		//Reset register for Exit Condition
    	setAuxRegister(0);
    	
    	writeStmt("\rEXIT_" + tempCounter + ":");
    	writeStmt("nop");
    
    	labelCounter++;

        return null;
	}

	/**
	 * Visit ReturnNode of the AST.
     * @param node The ReturnNode reference
	 */
    public Object visit(ReturnNode node)
	{
    	// This is a flag to verify if 'return' key is needed.
    	returned = true;
    	
    	// Visit value to return
    	GenNodeInfo retInfo = (GenNodeInfo)node.visitValue(this);

    	// If it exists, push in stack and write code
    	if(retInfo != null)    	    	  
    		pushInStack(node, retInfo);
    	
    	// If an array is returned, check the type to choose right keyword
    	if(retInfo.getKind() == IdType.ARRAYNODE)
    		writeStmt("areturn");
    	else
    	{
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

		return null;
    }

    /**
     * Visit SubNode of the AST.
     * @param node The SubNode reference
     */
    public Object visit(SubNode node)
    {
    	// Visit left and right hand of SubNode
    	IdType type = visitBinaryNode(node, Operator.DIFF);

    	return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }

    /**
     * Visit DivNode of the AST.
     * @param node The DivNode reference
     */
    public Object visit(DivNode node)
    {
    	// Visit left and right hand of DivNode
    	IdType type = visitBinaryNode(node, Operator.DIV);
    	
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }

    /**
     * Visit AddNode of the AST.
     * @param node The AddNode reference
     */
    public Object visit(AddNode node)
    {
    	// Visit left and right hand of AddNode
        IdType type = visitBinaryNode(node, Operator.PLUS);
        
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }

    /**
     * Visit MulNode of the AST.
     * @param node The MulNode reference
     */
    public Object visit(MulNode node)
    {
    	// Visit left and right hand of MulNode
    	IdType type = visitBinaryNode(node, Operator.MUL);
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }
    
    /**
     * Visit ModNode of the AST.
     * @param node The ModNode reference
     */
	public Object visit(ModNode node) 
	{
    	// Visit left and right hand of MulNode
		IdType type = visitBinaryNode(node, Operator.MOD);
		
        return new GenNodeInfo("", IdType.NULL, "", type, 0);
	}

    /**
     * Visit OrNode of the AST.
     * @param node The OrNode reference
     */
    public Object visit(OrNode node)
    {
    	// Visit left hand
    	GenNodeInfo left = (GenNodeInfo) node.visitLeft(this);
    	analyzeCondition(node, left);
		
    	// Visit right hand
		GenNodeInfo right = (GenNodeInfo) node.visitRight(this);
		analyzeCondition(node, right);
		
		return new GenNodeInfo("", IdType.NULL, "", IdType.BOOL, 0);
    }

    /**
     * Visit AndNode of the AST.
     * @param node The AndNode reference
     */
    public Object visit(AndNode node)
    {    
    	// Visit left hand
    	GenNodeInfo left = (GenNodeInfo) node.visitLeft(this);
    	analyzeCondition(node, left);
    	
    	// Write labels
    	writeLabel();
    	labelCounter++;

    	// Load register to check previous condition
		writeStmt("iload " + auxRegister);
		
		// If OK go to the next condition
		writeStmt("ifle EXIT_" + ifLabelCounter);
		writeStmt("goto #" + labelCounter);
		
		// Visit right hand
		GenNodeInfo right = (GenNodeInfo) node.visitRight(this);
		analyzeCondition(node, right);
        
		return new GenNodeInfo("", IdType.NULL, "", IdType.BOOL, 0);
    }
    
    /**
     * Write branch condition
     * @param node The OrNode/AndNode reference
     * @param info The GenNodeInfo reference
     */
    private void analyzeCondition(Node node, GenNodeInfo info)
    {   	
    	// Write a label to the output StringBuffer
    	writeLabel();
    	
    	//increase the counter
    	labelCounter++;
    
    	// if condition is a variable or a constant (boolean)
    	if (info.getKind() == IdType.VARIABLE || info.getKind() == IdType.CONST)
    	{
    		// write in stack
    		pushInStack(node, info);
    		
    		//create a comparison with 0    		
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		// Write true condition
    		writeTrue();
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
    			
    			// if the right hand is not a variable or a constant (it's a complex node)
    			if ((right.getKind() == IdType.NULL) || (right.getKind() == IdType.NEW)) {
    				writeStmt("i2f");
    				pushInStack(node, left);
		        	pushInStack(node, right);
    			}
    			else {
		        	pushInStack(node, left);
		        	pushInStack(node, right);
		        	writeStmt("i2f");
    			}
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
        			writeTrue();
        			
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
        			writeTrue();        			
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
        			writeTrue();        			
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
        			writeTrue();
        			break;
        			
    		case EQ:
	    			if(sOperator.equals("f")) {
	    				sOperator = "fsub\n";
	    				sOperator+= "ifeq " + "#" + labelCounter;
	    				sOperator+= "\n\tgoto #"+ ++labelCounter;
	    			}
	    			else
	    			{
	    				sOperator = "if_icmpeq " + "#" + labelCounter;
	    				sOperator+= "\n\tgoto #"+ ++labelCounter;
	    			}
	    			
        			writeStmt(sOperator);
        			writeTrue();	    			
	    			break;
        			
        	case NEQ:
	        		if(sOperator.equals("f")) {
	    				sOperator = "fsub\n";
	    				sOperator+= "ifne " + "#" + labelCounter;
	    				sOperator+= "\n\tgoto #"+ ++labelCounter;
	    			}
	    			else
	    			{
	    				sOperator = "if_icmpne " + "#" + labelCounter;
	    				sOperator+= "\n\tgoto #"+ ++labelCounter;
	    			}
	    			
	    			writeStmt(sOperator);
	    			writeTrue();	    			
	    			break;  
        		
        	default:
        			sOperator = "";
        			break;        			
    	}
		
		return retType;
    }
        
	private void pushInStack(Node node, GenNodeInfo info) {
		SymbolDesc varDesc = null;

		String name = info.getName();
		IdType type = info.getType();
		String value = ""; 
		IdType kind = info.getKind();
		varDesc = sTable.getVarDesc(name, node.getBlockNumber());
		int dim = info.getDim();

		if(kind != IdType.ARRAYCALLNODE)
			value = (String)info.getValue();
		
		if (dim == 0) {

			if (kind == IdType.VARIABLE) {

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
			if (kind == IdType.ARRAYNODE)
				writeStmt("aload " + varDesc.getJvmVar());
			else if (kind == IdType.ARRAYCALLNODE)
			{
				visitArrayCallNode(info);

				switch (type) {
				case BOOL:
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
	}
		
	private void popFromStack(Node node, GenNodeInfo info) {
		SymbolDesc varDesc = null;

		String name = info.getName();
		IdType type = info.getType();		
		varDesc = sTable.getVarDesc(name, node.getBlockNumber());
		int dim = info.getDim();

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
			if (info.getKind() == IdType.ARRAYNODE)
				writeStmt("astore " + varDesc.getJvmVar());
			else
			{
				switch (type) {
				case BOOL:
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

  	public Object visit(LETNode node) 
  	{
  		IdType type = visitBinaryNode(node, Operator.LET);
        
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}

  	public Object visit(GTNode node) 
	{	
  		IdType type = visitBinaryNode(node, Operator.GT);
          
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}

  	public Object visit(GETNode node) 
  	{
  		IdType type = visitBinaryNode(node, Operator.GET);
        
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}
  	
  	public Object visit(EqNode node)
    {
  		IdType type = visitBinaryNode(node, Operator.EQ);
        
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
    }

  	public Object visit(NotEqNode node)
  	{
  		IdType type = visitBinaryNode(node, Operator.NEQ);
        
  		return new GenNodeInfo("", IdType.NULL, "", type, 0);
  	}
	
	public Object visit(NotNode node) 
	{
		GenNodeInfo info = (GenNodeInfo)node.visitChild(this);

		if (info.getKind() != IdType.NULL && info.getKind() != IdType.NEW)
			pushInStack(node, info);
		
		return new GenNodeInfo("", IdType.NOTNODE, "", info.getType(), info.getDim());
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
	
	public Object visit(CastNode node) {
		GenNodeInfo info = (GenNodeInfo)node.visitChild(this);
		
		if (info.getKind() != IdType.NULL && info.getKind() != IdType.NEW)
			pushInStack(node, info);
		
		writeStmt("f2i");
		return new GenNodeInfo("", IdType.NULL, info.getValue(), info.getType(), info.getDim());
	}

	public Object visit(SignNode node) {
		GenNodeInfo info = (GenNodeInfo)node.visitChild(this);
		// if the kind of Node is a Variable or a Constant
		if (info.getKind() != IdType.NULL && info.getKind() != IdType.NEW) {
			pushInStack(node, info);
			
		}
		if (info.getType() == IdType.INT)
			writeStmt("ineg");
		if (info.getType() == IdType.FLOAT)
			writeStmt("fneg");
		return new GenNodeInfo("", IdType.NULL, info.getValue(), info.getType(), info.getDim());
	}

	public Object visit(WhileNode node) 
	{
		ifLabelCounter++;
		
		//Reset register for Exit Condition
		setAuxRegister(0);
		
    	writeStmt("\rWHILE_" + ifLabelCounter + ":");
    	
    	GenNodeInfo info = (GenNodeInfo) node.visitTest(this);
    	
    	if (info.getKind() == IdType.VARIABLE || info.getKind() == IdType.CONST)
    	{
    		pushInStack(node, info);
    		writeStmt("ifgt #" + ++labelCounter);
    		writeStmt("goto #" + ++labelCounter);
    		
    		writeTrue();
    	}
    	
    	//write exit block
    	writeLabel();
    	
    	writeStmt("iload " + auxRegister);
    	writeStmt("ifgt STMT_" + ifLabelCounter);
    	writeStmt("goto EXIT_" + ifLabelCounter);
    	
    	writeStmt("\rSTMT_" + ifLabelCounter + ":");
    	
    	int tempCounter = ifLabelCounter;
    	
    	node.visitWhile(this);  
    	
		//Reset register for Exit Condition
    	setAuxRegister(0);
    	
    	writeStmt("goto WHILE_" + tempCounter);
    	
    	writeStmt("\rEXIT_" + tempCounter + ":"); // TODO: Fix with labelCreator class
    
    	labelCounter++;

        return null;
	}

	public Object visit(NullNode letNode) {
		return null;
	}
	
	public Object visit(SimpleVarNode node) 
	{
		SymbolDesc varDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());
		GenNodeInfo info = null;

		if(varDesc.getDim() > 0)
			info = new GenNodeInfo(node.getName(), IdType.ARRAYNODE, "", varDesc.getType(), varDesc.getDim());
		else
			info = new GenNodeInfo(node.getName(), IdType.VARIABLE, "", varDesc.getType(), varDesc.getDim());

		return info;
	}
	
    private String getParamTypes(Node[] nodes)
    {
    	String sRet = "";
    	
    	for(int i=0;i<nodes.length;i++)
    		sRet += StuffCreator.getJVMType(nodes[i].getType());
    	
    	return sRet;
    }

    /**
     * Write true condition
     */
    private void writeTrue()
    {
    	// Set labelCounter to the previous value
    	labelCounter--;
    	writeLabel();
    	labelCounter++;
    	
    	// write 1 in the register
    	setAuxRegister(1);
    }
    
    //write 0/1 in the auxRegister
    private void setAuxRegister(int i)
    {
    	writeStmt("ldc " + i);
    	writeStmt("istore " + auxRegister);
    }
    
	public Object visit(ArrayNewNode node) {

		//String name = arrayNames.get(arrayCounter++);
		IdType type = node.getType();
		int dim = node.getDimension();
		node.visitDim(this);

		if (dim == 1) {
			// we use anewarray for string type
			if (type == IdType.STRING)
				writeStmt("anewarray " + "java/lang/String");
			else if (type == IdType.BOOL)
				writeStmt("newarray int");
			else
				// we use newarray for types int, float and bool
				writeStmt("newarray " + type);
		} else if (dim > 1) {
			// we use multianewarray for n-dimension array
			writeStmt("multianewarray " + StuffCreator.getOpenBrackets(dim) + StuffCreator.getJVMType(type) + " "
					+ dim);
		}
		return new GenNodeInfo("", IdType.NEW, "", type, dim);

	}

	public Object visit(ArrayCallNode node) {
		//SymbolDesc arrayDesc = sTable.getVarDesc(node.getName(),node.getBlockNumber());
		//writeStmt("aload " + arrayDesc.getJvmVar());

		GenNodeInfo infoVar = (GenNodeInfo) node.visitVar(this);
//		multiArrayDim = infoVar.getDim();
		
		//node.visitDim(this);

		return new GenNodeInfo(infoVar.getName(), IdType.ARRAYCALLNODE, node, infoVar.getType(), infoVar.getDim());
	}

	public Object visit(ArrayNode node) {

		SymbolDesc arrayDesc = sTable.getVarDesc(node.getName(), node.getBlockNumber());

		GenNodeInfo info = new GenNodeInfo(node.getName(), IdType.ARRAYNODE, "", arrayDesc.getType(), arrayDesc.getDim());

		return info;
	}

	public Object visit(ArraySizeNode node) {

		GenNodeInfo info = (GenNodeInfo) node.visitExpr(this);

		pushInStack(node, info);

		if (multiArrayDim > 1) 
			writeStmt("aaload");

		multiArrayDim--;
		
		return info;
	}
	private void visitArrayCallNode(GenNodeInfo info)
	{
		ArrayCallNode n = (ArrayCallNode)info.getValue();
		SymbolDesc arrayDesc = sTable.getVarDesc( n.getName(), n.getBlockNumber());
		writeStmt("aload " + arrayDesc.getJvmVar());
		
		multiArrayDim = info.getDim();
		
		n.visitDim(this);
	}	
}