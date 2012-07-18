package ast;

import it.m2j.IdType;
import compiler.*;


public class FunctionNode extends SlotNode
{
    protected ListNode params;
    private BlockNode body;
    private int dim;

    public FunctionNode(String name, ListNode params, IdType type, int dim, BlockNode body, int lineNumber, int colNumber)
    {
        super(name, type, lineNumber, colNumber);
        this.params = params;
        this.body = body;
        this.dim = dim;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public void visitParams(Visitor v)
    {    	
        if (params != null)
            params.accept(v);
    }

    public String toString()
    {
    	return name /*+ params.toString()*/;
    }

    public void visitBody(Visitor v)
    {    	
        if (body != null)
            body.accept(v);
    }

    public IdType getType()
    {
        return type;
    }

    public void setParams(ListNode params)
    {
        this.params = params;
    }
    
    public ListNode getParams()
    {
    	return this.params;
    }
    
    public int getDimension()
    {
    	return this.dim;
    }
}