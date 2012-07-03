package ast;

import compiler.Visitor;


public class AssignNode extends ExprNode
{
    private String var;
    private ExprNode value;
    private int block;
    
    public AssignNode(String var, ExprNode e, int block, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.var = var;
        this.block = block;
        value = e;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return var + " = " + value;
    }

    public Object visitValue(Visitor v)
    {
        return value.accept(v);
    }
    
    public String getVar()
    {
    	return var;
    }
    
    public ExprNode getValue()
    {
    	return value;
    }
    
    public int getBlock()
    {
    	return block;
    }
}