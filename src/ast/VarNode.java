package ast;

import compiler.*;

public class VarNode extends ExprNode
{
    private String name;

    //the static type of the variable
    //private Symbol type = null;
    //the symbol table entry for the variable
    //private VarSymbol symbol = null;

    private boolean field = false; //true if the variable is a field (ie this.var)
    //private FieldSymbol fieldSymbol = null;

    public VarNode(String name, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.name = name;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public static String THIS = "this";

    public boolean isThis()
    {
        return name.equals(THIS);
    }

    public String toString()
    {
        return name;
    }
    public String getName()
    {
        return name;
    }

//    public String getType()
//    {
//        if (type == null)
//            return "unknown";
//        else
//            return type.getName();
//    }
//
//    public Symbol getTypeSymbol()
//    {
//        return type;
//    }
//
//    public void setType(Symbol type)
//    {
//        this.type = type;
//    }
//    public void setSymbol(VarSymbol symbol)
//    {
//        this.symbol = symbol;
//    }
//    public VarSymbol getSymbol()
//    {
//        if (field)
//            throw new RuntimeException("Attempt to get variable symbol, but only has field symbol");
//
//        return symbol;
//    }
//    public boolean isField()
//    {
//        return field;
//    }
//    public FieldSymbol getFieldSymbol()
//    {
//        if (!field)
//            throw new RuntimeException("Attempt to get field symbol, but only has variable symbol");
//
//        return fieldSymbol;
//    }
//    public void setFieldSymbol(FieldSymbol fieldSymbol)
//    {
//        field = true;
//        this.fieldSymbol = fieldSymbol;
//    }
}