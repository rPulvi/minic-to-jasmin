package compiler;

import ast.*;
import java.io.PrintWriter;


public abstract class Visitor
{
    //output
    protected PrintWriter err;
    //keep track of the number of errors
    protected int errorCount = 0;

    public Visitor(PrintWriter out)
    {
        this.err = out;
    }

    /**
     * returns the number of errors that occured during the visit
     * @return
     */
    public int getErrorCount()
    {
        return errorCount;
    }

    //report and log an error
    protected void error(String msg, Node node)
    {
        System.out.println("  " + msg + "'" + node.toString() + "' on line " + node.getLineNumber() + ", column " + node.getColumnNumber());
        ++errorCount;
    }

    public abstract Object visit(AddNode node);
    public abstract Object visit(FloatNode node);
    public abstract Object visit(AndNode node);
    public abstract Object visit(ArgNode node);
    public abstract Object visit(AssignNode node);
    public abstract Object visit(BoolNode node);    
    public abstract Object visit(BlockNode node);
    public abstract Object visit(DeclNode node);
    public abstract Object visit(DivNode node);
    public abstract Object visit(EqNode node);        
    public abstract Object visit(IfNode node);
    public abstract Object visit(ListNode node);
    public abstract Object visit(FuncCallNode node);
    public abstract Object visit(FunctionNode node);
    public abstract Object visit(MulNode node);
    public abstract Object visit(NegNode node);
    public abstract Object visit(NewNode node);
    public abstract Object visit(NotEqNode node);
    public abstract Object visit(NotNode node);
    public abstract Object visit(IntNode node);
    public abstract Object visit(OrNode node);
    public abstract Object visit(PrintNode node);
    public abstract Object visit(ReturnNode node);
    public abstract Object visit(StringNode node);
    public abstract Object visit(SubNode node);    
    public abstract Object visit(VarNode node);    
	public abstract Object visit(IfElseNode node);
	public abstract Object visit(CastNode node);
	public abstract Object visit(ModNode modNode);
	public abstract Object visit(LTNode letNode);
	public abstract Object visit(LETNode letNode);
	public abstract Object visit(GTNode letNode);
	public abstract Object visit(GETNode letNode);
	public abstract Object visit(SignNode letNode);
	public abstract Object visit(WhileNode letNode);
	public abstract Object visit(NullNode letNode);	
}