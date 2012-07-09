package ast;

import compiler.Visitor;

public class ArrayCallNode extends ExprNode{	
	private ListNode listDim;
	private VarNode varNode;
	
    public ArrayCallNode(VarNode varNode, ListNode listDim, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.varNode = varNode;
        this.listDim = listDim;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }
    
    public Object visitDim(Visitor v)
    {
    	return this.listDim.accept(v);
    }
    
    public Object visitVar(Visitor v)
    {
    	return this.varNode.accept(v);    	
    }

    public String toString()
    {
        return varNode + " " + this.listDim;
    }

    public int getDimension()
    {
    	return this.listDim.size();    	
    }
    
    public String getName()
    {
    	return varNode.toString();
    }
}