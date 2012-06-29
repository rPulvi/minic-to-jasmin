package ast;

import compiler.Visitor;

public class WhileNode extends Node{

	public WhileNode(ExprNode condition, BlockNode block, int lineNumber, int colNumber) {
		super(lineNumber, colNumber);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object accept(Visitor v) {
		// TODO Auto-generated method stub
		return null;
	}



}
