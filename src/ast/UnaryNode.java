package ast;

import compiler.Visitor;

public abstract class UnaryNode extends ExprNode
{
    protected Node child = null;

    public UnaryNode(Node child, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.child = child;
    }

    public Object visitChild(Visitor v)
    {
        return child.accept(v);
    }
}