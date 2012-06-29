package ast;

import it.m2j.IdType;
import compiler.*;


public class ArgNode extends Node
{
    private IdType type;
    private String name;

    //private ParamSymbol symbol = null;

    public ArgNode(String name, IdType type, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.name = name;
        this.type = type;
    }

    //For "Extern" function declaration
    public ArgNode(IdType type, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.name = "";
        this.type = type;
    }
    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public IdType getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return type.toString() + " " + name;
    }
    /*
    public void setSymbol(ParamSymbol symbol)
    {
        this.symbol = symbol;
    }
    public ParamSymbol getSymbol()
    {
        return symbol;
    }
    */
}