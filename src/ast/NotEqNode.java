package ast;

import compiler.Visitor;


public class NotEqNode extends BinaryNode
{
    public NotEqNode(ExprNode e1, ExprNode e2, int lineNumber, int colNumber)
    {
        super(e1, e2, lineNumber, colNumber);
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return left + " != " + right;
    }
}