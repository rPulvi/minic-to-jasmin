package ast;

import compiler.Visitor;


public class AssNode extends ExprNode
{
    private ExprNode var;
    private ExprNode value;

    public AssNode(ExprNode var, ExprNode e, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.var = var;
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

    public Object visitTarget(Visitor v)
    {
        return var.accept(v);
    }

    public Object visitValue(Visitor v)
    {
        return value.accept(v);
    }
}