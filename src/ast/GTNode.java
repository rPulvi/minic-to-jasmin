package ast;

import compiler.Visitor;

public class GTNode extends BinaryNode{

	public GTNode(Node left, Node right, int lineNumber, int colNumber) 
	{
		super(left, right, lineNumber, colNumber);
	}

	@Override
	public Object accept(Visitor v) 	
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return left + " > " + right;
	}


}
