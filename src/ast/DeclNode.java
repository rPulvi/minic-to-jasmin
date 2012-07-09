package ast;

import it.m2j.IdType;
import compiler.*;


public class DeclNode extends ExprNode
{
    private VarNode vNode;
    private IdType type;

    public DeclNode(VarNode node, IdType type, int lineNumber, int colNumber)
    {
        super(node.getBlockNumber(), lineNumber, colNumber);
        this.type = type;
        vNode = node;
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
        return type + " " + vNode.toString();
    }
    
    public String getName()
    {
    	return vNode.getName();
    }
    
    public IdType getType()
    {
    	return type;
    }       
}