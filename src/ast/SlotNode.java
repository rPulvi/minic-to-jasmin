package ast;

import it.m2j.IdType;
import compiler.*;

public abstract class SlotNode extends Node
{
    protected IdType type;
    protected String name;
    
    public SlotNode(String name, IdType type, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);        
        this.type = type;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}