package ast;

import it.m2j.IdType;
import compiler.Visitor;

public class ArrayNode extends ExprNode{

	private IdType type;
	
	public ArrayNode(IdType type, int lineNumber, int colNumber) 
	{
		super(lineNumber, colNumber);
		this.type = type;
	}

	@Override
	public Object accept(Visitor v)  
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return "This is a fuckin' array bro!";
	}
	
	public IdType getType()
	{
		return type;
	}
}
