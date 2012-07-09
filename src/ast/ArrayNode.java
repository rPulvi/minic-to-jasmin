package ast;

import compiler.Visitor;

public class ArrayNode extends VarNode{

	private int dim;
	
	public ArrayNode(String name, int dim, int blockNumber, int lineNumber, int colNumber) 
	{
		super(name, blockNumber, lineNumber, colNumber);
		this.dim = dim;
	}

	public Object accept(Visitor v)  
	{
		return v.visit(this);
	}	
	
	public String toString()
	{
		return /*type + " " + */ this.getName() + getBrackets();
	}
	
	public int getDimension()
	{
		return this.dim;
	}
	
	private String getBrackets()
	{
		String sRet="";
		
		for(int i=0; i<dim; i++)
		{
			sRet += "[]";
		}
		
		return sRet;
		
	}
}
