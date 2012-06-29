package ast;

import it.m2j.IdType;
import compiler.Visitor;

public class FunctionExtNode extends SlotNode{
	
	protected ListNode params;
	
	public FunctionExtNode(String name, IdType type, ListNode params, int lineNumber, int colNumber) {
		
		super(name, type, lineNumber, colNumber);
		this.params = params;
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



}
