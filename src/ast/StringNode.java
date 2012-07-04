package ast;

import it.m2j.IdType;
import compiler.Visitor;
//import utils.ParamUtils;


public class StringNode extends ExprNode
{
    private String value;

    public StringNode(String value, int lineNumber, int colNumber)
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
        return value;
    }

    public IdType getType()
    {
        return IdType.STRING;
    }
}