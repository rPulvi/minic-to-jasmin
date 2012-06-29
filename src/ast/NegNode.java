package ast;

import compiler.Visitor;


public class NegNode extends UnaryNode
{
    public NegNode(ExprNode e, int lineNumber, int colNumber)
    {
        super(e, lineNumber, colNumber);
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return "-" + child;
    }
}