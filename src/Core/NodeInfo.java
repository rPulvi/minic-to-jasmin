package aux;

import utils.StuffCreator;

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
	
	public String toString()
	{
		return type + StuffCreator.getBrackets(dim);
	}
}