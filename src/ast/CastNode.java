package ast;

import compiler.Visitor;

public class CastNode extends UnaryNode {

	public CastNode(ExprNode e, int lineNumber, int colNumber) {
		super(e, lineNumber, colNumber);
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
	
	public String toString() {
	    return "(int)" + child;
	}
}
