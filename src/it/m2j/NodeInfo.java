package it.m2j;

public class NodeInfo {

	private IdType type;
	private int dim;
	
	public NodeInfo(IdType type, int dim)
	{
		this.type = type;
		this.dim = dim;
	}
	
	public IdType getType()
	{
		return this.type;
	}
	
	public int getDim()
	{
		return this.dim;
	}
}