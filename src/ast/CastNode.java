package ast;

import compiler.Visitor;

public class CastNode extends UnaryNode {
	//private ExprNode value;  // value of the expression for casting 

	public CastNode(ExprNode e, int lineNumber, int colNumber) {
		super(e, lineNumber, colNumber);
		//this.value = e;
	}

	@Override
	public Object accept(Visitor v) {
		return v.visit(this);
	}
	
	public String toString() {
	    return "(int)" + child;
	}
	
//	public ExprNode getValue() {
//	    	return value;
//	}
	
//	public Object visitValue(Visitor v)
//    {
//        return value.accept(v);
//    }

}
