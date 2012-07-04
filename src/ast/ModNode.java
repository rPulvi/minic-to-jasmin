package ast;

import compiler.Visitor;

public class ModNode extends BinaryNode {

	public ModNode(Node left, Node right, int lineNumber, int colNumber) 
	{
		super(left, right, lineNumber, colNumber);
	}
	
	public Object accept(Visitor v) 
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return left + " % " + right;
	}
}
