package it.m2j;

public class SymbolId {
	
	private String ID;
	private int nBlock;
	
	public SymbolId(String id, int block)
	{
		this.ID = id;
		this.nBlock = block;
	}
	
	public String getId()
	{
		return this.ID;		
	}
	
	public int getBlock()
	{
		return this.nBlock;
	}
}
