package ast;

import compiler.Visitor;

public class ReturnNode extends Node
{
    private ExprNode value;
    public ReturnNode(ExprNode value, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.value = value;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public Object visitValue(Visitor v)
    {
        if (value == null)
            return null;
        else
            return value.accept(v);
    }

    public boolean noReturnValue()
    {
        return value == null;
    }

    public String toString()
    {
        if (value == null)
            return "return;";
        return "return " + value + ";";
    }
}