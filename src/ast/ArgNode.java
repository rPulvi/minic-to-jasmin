package ast;

import aux.IdType;
import compiler.*;


public class ArgNode extends Node
{
    private IdType type;
    private String name;
    private int dim;
    //private ParamSymbol symbol = null;

    public ArgNode(String name, IdType type, int dim, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.name = name;
        this.type = type;
        this.dim = dim;
    }

    //For "Extern" function declaration
    public ArgNode(IdType type, int dim, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.name = "_" + lineNumber + "_" + colNumber; //temp variables.
        this.type = type;
        this.dim = dim;
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

    public int getDimension()
    {
    	return this.dim;
    }
}