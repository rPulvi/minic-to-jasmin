package ast;

import it.m2j.IdType;
import compiler.*;

public class VarNode extends ExprNode
{
    private String name;
    private IdType type;        

    public VarNode(String name, int blockNumber, int lineNumber, int colNumber)
    {
        super(blockNumber, lineNumber, colNumber);        
        this.name = name;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return  name;
    }
    
    public String getName()
    {
        return name;
    }
    public void setType(IdType type)
    {
    	this.type = type;
    }
    
    public IdType getType()
    {
    	return this.type;
    }
}