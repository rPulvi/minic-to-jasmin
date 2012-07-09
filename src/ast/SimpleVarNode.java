package ast;

import compiler.Visitor;

public class SimpleVarNode extends VarNode {

	public SimpleVarNode(String name, int blockNumber, int lineNumber, int colNumber) {
		super(name, blockNumber, lineNumber, colNumber);
	}
	
	public Object accept(Visitor v)  
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return /*type + " " + */ this.getName();
	}
}
