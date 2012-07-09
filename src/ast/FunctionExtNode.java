package ast;

import it.m2j.IdType;
import compiler.Visitor;

public class FunctionExtNode extends SlotNode{
	
	protected ListNode params;
	private int dim;
	
	public FunctionExtNode(String name, IdType type, int dim, ListNode params, int lineNumber, int colNumber) {
		
		super(name, type, lineNumber, colNumber);
		this.params = params;
		this.dim = dim;
	}

	@Override
	public Object accept(Visitor v) {
		// TODO Auto-generated method stub
		return null;
	}

    public void setParams(ListNode params)
    {
        this.params = params;
    }
    
    public IdType getType()
    {
        return type;
    }
    
    public int getDimension()
    {
    	return this.dim;
    }
}
