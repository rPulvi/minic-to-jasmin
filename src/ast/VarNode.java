package ast;

import compiler.*;

public abstract class VarNode extends ExprNode
{
    private String name;     

    public VarNode(String name, int blockNumber, int lineNumber, int colNumber)
    {
        super(blockNumber, lineNumber, colNumber);        
        this.name = name;
    }

//    public Object accept(Visitor v)
//    {
//        return v.visit(this);
//    }

    public String toString()
    {
        return name;
    }
    
    public String getName()
    {
        return name;
    }    
}