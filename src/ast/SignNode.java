package ast;

import compiler.Visitor;

public class SignNode extends UnaryNode
{
	private String sign;
	
	public SignNode(String sign, Node child, int lineNumber, int colNumber) 
	{
		super(child, lineNumber, colNumber);
		this.sign = sign;
	}

	@Override
	public Object accept(Visitor v)
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return sign + child;
	}

}
