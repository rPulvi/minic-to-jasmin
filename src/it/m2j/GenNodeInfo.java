package it.m2j;

public class GenNodeInfo {
	private String name;
	private String value;
	private IdType type;
	private int dim;
	
	public GenNodeInfo(String name, String value, IdType type, int dimension)
	{
		this.name = name;
		this.value = value;
		this.type = type;
		this.dim = dimension;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public IdType getType()
	{
		return type;
	}
	
	public int getDim()
	{
		return dim;
	}
	
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setType(IdType type) {
		this.type = type;
	}
}
