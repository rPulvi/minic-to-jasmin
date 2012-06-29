package ast;

import compiler.*;


public class BracketNode extends UnaryNode
{
    public BracketNode(ExprNode expr, int line, int col)
    {
        super(expr, line, col);
    }
    public Object accept(Visitor v)
    {
        return child.accept(v);
    }

    public String toString()
    {
        return "(" + child + ")";
    }
}