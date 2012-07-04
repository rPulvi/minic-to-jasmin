package ast;

import compiler.Visitor;

public class LTNode extends BinaryNode {

	public LTNode(Node left, Node right, int lineNumber, int colNumber) 
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
		return left + " < " + right;
	}

}
