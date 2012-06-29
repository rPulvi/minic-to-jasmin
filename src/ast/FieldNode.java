package ast;

import it.m2j.IdType;
import compiler.*;


public class FieldNode extends SlotNode implements NodeWithValue
{
    private ExprNode value;

    //private FieldSymbol symbol = null;

   
    public FieldNode(String name, IdType type, ExprNode value, int lineNumber, int colNumber)
    {
        super(name, type, lineNumber, colNumber);
        this.value = value;
    }   

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public IdType getType()
    {
        return type;
    }

    public Object visitValue(Visitor v)
    {
        return value.accept(v);
    }

//    public String toString()
//    {
//        return access + " " + type + " " + name + " = " + value;
//    }
//    public void setSymbol(FieldSymbol symbol)
//    {
//        this.symbol = symbol;
//    }
//    public FieldSymbol getSymbol()
//    {
//        return symbol;
//    }
}