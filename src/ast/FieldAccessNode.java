package ast;

import compiler.*;


public class FieldAccessNode extends ExprNode
{
    private ExprNode target;
    private String fieldName;

    //private FieldSymbol symbol = null;
    //private ClassSymbol targetSymbol = null;

    public FieldAccessNode(ExprNode target, String fieldName, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.target = target;
        this.fieldName = fieldName;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return target + "." + fieldName;
    }

    public Object visitTarget(Visitor v)
    {
        return target.accept(v);
    }

    public String getName()
    {
        return fieldName;
    }
    /*
    public FieldSymbol getSymbol()
    {
        return symbol;
    }
    public void setSymbol(FieldSymbol symbol)
    {
        this.symbol = symbol;
    }
    public ClassSymbol getTargetSymbol()
    {
        return targetSymbol;
    }
    public void setTargetSymbol(ClassSymbol targetSymbol)
    {
        this.targetSymbol = targetSymbol;
    }
    */
}