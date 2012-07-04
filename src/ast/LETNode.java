package ast;

import compiler.Visitor;

public class LETNode extends BinaryNode{

	public LETNode(Node left, Node right, int lineNumber, int colNumber) 
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
		return left + " <= " + right;
	}
	

}
