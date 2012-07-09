package ast;

import compiler.Visitor;

public class ArraySizeNode extends Node{

	private ExprNode exprNode;
	
	public ArraySizeNode(ExprNode exprNode, int lineNumber, int colNumber) {
		super(lineNumber, colNumber);
		this.exprNode = exprNode;				
	}

	public Object accept(Visitor v) 
	{
		return v.visit(this);
	}
	
	public Object visitExpr(Visitor v)
	{
		return exprNode.accept(v);
	}
	
	public String toString()
	{
		return "[" + exprNode + "]";
	}
}
