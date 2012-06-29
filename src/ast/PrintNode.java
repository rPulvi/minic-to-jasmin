package ast;

import it.m2j.IdType;
import compiler.Visitor;


public class PrintNode extends Node
{
    private ExprNode value;
    private IdType type = null; //the type of the value (either int, boolean or a class)

    public PrintNode(ExprNode value, int lineNumber, int colNumber)
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
        return value.accept(v);
    }

    public String toString()
    {
        return "print(" + value + ")";
    }

    public IdType getType()
    {
        return type;
    }

    public void setType(IdType type)
    {
        this.type = type;
    }
}