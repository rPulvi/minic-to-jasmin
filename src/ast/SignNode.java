package ast;

import compiler.Visitor;

public class SignNode extends UnaryNode
{

	public SignNode(Node child, int lineNumber, int colNumber) {
		super(child, lineNumber, colNumber);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object accept(Visitor v) {
		// TODO Auto-generated method stub
		return null;
	}

}
