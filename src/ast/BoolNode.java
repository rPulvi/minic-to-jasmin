package ast;

import aux.IdType;
import compiler.Visitor;


public class BoolNode extends ExprNode
{
    private boolean value;

    public BoolNode(boolean value, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.value = value;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        if (value)
            return "true";
        else
            return "false";
    }

    public boolean getValue()
    {
        return value;
    }

    public IdType getType()
    {
        return IdType.BOOL;
    }
}