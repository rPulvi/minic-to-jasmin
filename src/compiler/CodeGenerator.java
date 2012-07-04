package compiler;

import java.io.FileWriter;
import java.io.PrintWriter;

import ast.*;

public class CodeGenerator extends Visitor
{
    private StringBuffer out = new StringBuffer();
    private SymbolTable sTable; //the top level symbol table
    private String jasminClassName;
    //private SymbolTable curTable; //the current scope's symbol table

    //private CodeGenerator codeGen;
    
    //this variable is true when the result of an expression will be used
    //when it is false the statement should not leave anything on the stack
    //private boolean inExpr = false;

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
//        curTable = st;
//        this.codeGen = codeGen;
        
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

    //store an int on the heap at the next available index
    private void storeOnHeap(int x)
    {
//        writeStmt("aload 1");  //push the heap onto the stack
//        writeStmt("iload 2");  //push the heap index onto the stack
//        writeStmt("ldc " + x);  //push the value to store onto the stack
//        writeStmt("iastore");  //store it in the heap
//        writeStmt("iinc 2 1"); //increment the heap index
    }

    public Object visit(DeclNode node)
    {
//        inExpr = true;
//
//        VariableSymbol varSymbol = node.getSymbol();
//
//        writeStmt(";" + node.toString() + "----------------------------");
//        //get the value and leave it on the stack
//        node.visitValue(this);
//        //store in a new local variable
//        int local = NumberGenerator.getInstance().getLocal();
//        writeStmt("istore " + local);
//        //store the local variable in the symbol table
//        varSymbol.setLocalVar(local);
//
//        inExpr = false;
        return null;
    }

    //if we are in the lhs of an assignment statement
    //private boolean assignLHS = false;
    
    public Object visit(AssignNode node)
    {
//        boolean oldExpr = inExpr;
//        inExpr = true;
//        writeStmt(";" + node.toString() + "-----------------------------------");
//        //save register 5
//        CodeGenerator.pushVar(5, out);
//
//        //visit the lhs
//        assignLHS = true;
//        //the return value is the name of the local variable to assign to
//        String localVar = (String)node.visitTarget(this);
//        assignLHS = false;
//
//        if (localVar == null)  //store the result on the heap
//        {
//            writeStmt("istore 5");
//            node.visitValue(this); //visit the rhs and leave the value on top of the stack
//            writeStmt("aload 1");
//            writeStmt("swap");
//            writeStmt("iload 5");
//            writeStmt("swap");
//            writeStmt("iastore");
//        }
//        else   //store the result in a local variable
//        {
//            //visit the rhs and leave the value on top of the stack
//            node.visitValue(this);
//            //store the result in the determined local variable
//            writeStmt("istore " + localVar);
//        }
//
//        //restore register 5
//        CodeGenerator.popToVar(5, out);
//
//        inExpr = oldExpr;
        return null;
    }

    public Object visit(BlockNode node)
    {
//        //move scope
//        curTable = node.getSymbol().getSymbolTable();
//
//        node.visitChildren(this);
//
//        //restore the scope
//        curTable = curTable.getParent();

        return null;
    }

    public Object visit(VarNode node)
    {
//        if (node.isThis())
//        {
//            writeStmt("iload 0"); //put the this pointer on the stack
//            return null;
//        }
//
//        //this.var - field access
//        if (node.isField())
//        {
//            writeStmt("iload 0"); //push the address of 'this' onto the stack
//
//            FieldSymbol field = node.getFieldSymbol();
//            loadField(field, curClass, node);
//
//            return null;
//        }
//        else
//        //a local variable
//        {
//            if (assignLHS)
//            {
//                //find the local variable name from the symbol table and return it
//                return String.valueOf(node.getSymbol().getLocalVar());
//            }
//
//            //load the value onto the stack
//            writeStmt("iload " + node.getSymbol().getLocalVar());
//
//            return null;
//        }
    	
    	//da levare...
    	return null;
    }

    //its not really magic! If we wanted local vars (y) 7, 6, 5 but have (x) 5, 6, 7
    //then pMN = 7 + 5 = 12 and y = pMN - x, eg 12 - 5 = 7
    private int paramMagicNumber = -1;

    public Object visit(FunctionNode node)
    {
//        visitMethod(node);

        return null;
    }

    //extracted functionallity to visit either a constructor or method node
    private void visitMethod(FunctionNode node)
    {
//        MethodSymbol symbol = node.getSymbol();
//
//        NumberGenerator.getInstance().resetLocals();
//
//        writeStmt("\n;" + symbol.getKey() + "-----------------------------");
//        writeStmt(NumberGenerator.getInstance().makeMethodLabel(symbol.getMethodNumber()) + ":");
//
//        //store all the params as local variables
//        //l --> r
//        //we do this in r --> l order so for meth(int i1, int i2, int i3) the parameters
//        //should be on the stack in order ... i1, i2, i3, top of stack.
//        paramMagicNumber = symbol.getParamTypeArray().length + 2*NumberGenerator.getInstance().getTotalLocals() - 1;
//        node.visitParams(this);
//
//        //pop this
//        CodeGenerator.popToStack(out);
//        writeStmt("istore 0");
//
//        curTable = symbol.getSymbolTable();
//        node.visitBody(this);
//        curTable = curTable.getParent();
//
//        if (symbol.getType().equals("void"))
//        {
//            CodeGenerator.pushConst(0, out);
//            writeStmt("goto returnTable");
//        }
//        else
//        {
//            writeStmt("ldc \"Method '" + symbol.getKey() + "' never returns\"");
//            writeStmt("goto errorMsg");
//        }
//
//        writeStmt(";end of " + symbol.getKey() + "-----------------------------");
    }

    public Object visit(ArgNode node)
    {
//        int local = NumberGenerator.getInstance().getLocal();
//        ParamSymbol symbol = node.getSymbol();
//        symbol.setLocalVar(paramMagicNumber - local);
//        CodeGenerator.popToStack(out);
//        writeStmt("istore " + (paramMagicNumber - local));

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

    public Object visit(NewNode node)
    {
//        ClassSymbol classSymbol = node.getClassSymbol();
//
//        writeStmt("\n;" + node.toString() + "---------------");
//        //save register 5
//        CodeGenerator.pushVar(5, out);
//
//        CodeGenerator.newObject(classSymbol, out);
//        writeStmt("istore 5");
//
//        //call the constructor
//        MethodSymbol method = node.getSymbol();
//
//        boolean oldExpr = inExpr;
//        inExpr = true;
//        int retAdd = pushPreMethodCall(node, 5);
//        inExpr = oldExpr;
//
//        //figure out the method number (trivial)
//        writeStmt("ldc " + method.getMethodNumber());  //now have the method number
//
//        //use the jump table
//        writeStmt("goto jumpTable");
//
//        //the return label
//        writeStmt(NumberGenerator.getInstance().makeRetAdd(retAdd) + ":");
//
//        //pop the result (void so we can ignore it)
//        //writeStmt("pop");
//        CodeGenerator.pop(out);
//
//        //restore the local variables and 'this'
//        restoreVariables();
//
//        //leave a pointer to the new object on the stack
//        if (inExpr)
//            writeStmt("iload 5");
//
//        //restore register 5
//        CodeGenerator.popToVar(5, out);
//
//
//        writeStmt("; end of " + node.toString() + "---------------");

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
//        boolean oldExpr = inExpr;
//        inExpr = true;
//        writeStmt(";" + node.toString());
//
//        if (node.noReturnValue()) //return;
//            CodeGenerator.pushConst(0, out);
//        else  //return x;
//        {
//            node.visitValue(this);
//            CodeGenerator.pushFromStack(out);
//        }
//
//        writeStmt("goto returnTable");
//        inExpr = oldExpr;
    	
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
        visitBinaryNode(node, "isub");
        return null;
    }

    public Object visit(DivNode node)
    {
        visitBinaryNode(node, "idiv");
        return null;
    }

    public Object visit(AddNode node)
    {
        visitBinaryNode(node, "iadd");
        return null;
    }
    
    public Object visit(MulNode node)
    {
        visitBinaryNode(node, "imul");
        return null;
    }

    public Object visit(OrNode node)
    {
        visitBinaryNode(node, "ior");
        return null;
    }

    public Object visit(AndNode node)
    {
        visitBinaryNode(node, "iand");
        return null;
    }

    //visit &&, ||, +, -, * or / nodes
    private void visitBinaryNode(BinaryNode node, String op)
    {
//        writeStmt(";" + node.toString());
//
//        //save register 5
//        CodeGenerator.pushVar(5, out);
//
//        //push the two arguments on the stack
//        node.visitLeft(this);
//        writeStmt("istore 5");
//        node.visitRight(this);
//        writeStmt("iload 5");
//        writeStmt("swap");
//
//        //operate on the top two stack values
//        writeStmt(op);
//
//        //restore register 5
//        CodeGenerator.popToVar(5, out);
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
//        writeStmt(";" + node.toString());
//        //push the String onto the stack
//        writeStmt("ldc " + node.toString());
//        //convert the java.lang.String to a new String object
//        codeGen.newString(out);
        
        return null;
    }

    public Object visit(BoolNode node)
    {
        //push the boolean onto the stack

        //booleans are represented as 0/1
//        if (node.getValue())
//            writeStmt("iconst_1");
//        else
//            writeStmt("iconst_0");

        return null;
    }

    public Object visit(IntNode node)
    {
        //push the number onto the stack
//        writeStmt("ldc " + node.toString());
        return null;
    }

	@Override
	public Object visit(FloatNode node) {
		// TODO Auto-generated method stub
		return null;
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
}
