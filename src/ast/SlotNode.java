package ast;

import aux.IdType;

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