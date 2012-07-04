package ast;

import compiler.Visitor;

public class GETNode extends BinaryNode{

	public GETNode(Node left, Node right, int lineNumber, int colNumber) 
	{
		super(left, right, lineNumber, colNumber);
	}

	
	public Object accept(Visitor v) 
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return left + " >= " + right;
	}

	

}
