package it.m2j;

import ast.ListNode;

public class ExtNodeInfo {
	private String name;
	private String path;
	protected ListNode params;
	private int dim;
	private IdType retType;
	
	public ExtNodeInfo(String name, String path, ListNode params, int dim, IdType retType)
	{
		this.name = name;
		this.path = path;
		this.params = params;
		this.dim = dim;
		this.retType = retType;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public ListNode getParams()
	{
		return params;
	}
	
	public int getDim()
	{
		return dim;
	}
	
	public IdType getType()
	{
		return retType;
	}
}
