package ast;

import it.m2j.IdType;
import compiler.Visitor;


public class IntNode extends ExprNode
{
    private int value;

    public IntNode(Integer value, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.value = value.intValue();
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    public IdType getType()
    {
        return IdType.INT;
    }
}