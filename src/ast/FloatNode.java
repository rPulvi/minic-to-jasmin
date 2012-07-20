package ast;

import aux.IdType;
import compiler.Visitor;

public class FloatNode extends ExprNode{

	private float value;

    public FloatNode(Float value, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.value = value.floatValue();                
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
        return IdType.FLOAT;
    }

}
