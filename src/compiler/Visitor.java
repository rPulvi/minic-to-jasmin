package compiler;

import ast.*;

public abstract class Visitor
{
    //keep track of the number of errors
    protected int errorCount = 0;

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
    public abstract Object visit(NotEqNode node);
    public abstract Object visit(NotNode node);
    public abstract Object visit(IntNode node);
    public abstract Object visit(OrNode node);    
    public abstract Object visit(ReturnNode node);
    public abstract Object visit(StringNode node);
    public abstract Object visit(SubNode node);
	public abstract Object visit(IfElseNode node);
	public abstract Object visit(CastNode node);
	public abstract Object visit(ModNode node);
	public abstract Object visit(LTNode node);
	public abstract Object visit(LETNode node);
	public abstract Object visit(GTNode node);
	public abstract Object visit(GETNode node);
	public abstract Object visit(SignNode node);
	public abstract Object visit(WhileNode node);
	public abstract Object visit(NullNode node);
	public abstract Object visit(ArrayNode node);
	public abstract Object visit(ArrayNewNode node);
	public abstract Object visit(ArrayCallNode node);
	public abstract Object visit(SimpleVarNode node);
	public abstract Object visit(ArraySizeNode node);
	public abstract Object visit(FunctionExtNode node);
}