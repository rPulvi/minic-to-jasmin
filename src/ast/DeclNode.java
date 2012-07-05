package ast;

import it.m2j.IdType;
import compiler.*;


public class DeclNode extends ExprNode
{

    private VarNode vNode;

    //private VariableSymbol symbol = null;

    public DeclNode(VarNode e, IdType type, int lineNumber, int colNumber)
    {
        super(e.getBlockNumber(), lineNumber, colNumber);
        vNode = e;
        vNode.setType(type);
    }   
    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public Object visitVar(Visitor v)
    {
    	return vNode.accept(v);
    }
    
    public String toString()
    {
        return vNode.toString();
    }
    
    public String getName()
    {
    	return vNode.getName();
    }
    
    public IdType getType()
    {
    	return vNode.getType();
    }
}