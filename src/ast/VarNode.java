package ast;

import it.m2j.IdType;
import compiler.*;

public class VarNode extends ExprNode
{
    private String name;
    private Object type;
    private int block;        

    public VarNode(String name, Object type, int block, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);        
        this.name = name;
        this.type = type;
        this.block = block;        
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return type.toString() + " " + name;
    }
    public String getName()
    {
        return name;
    }

    public IdType getType()
    {
    	if (type instanceof Node)
    		return ((Node)type).getType();
    	else
    		return (IdType)type;
    }
    
    public int getBlockNumber()
    {
    	return block;
    }
}