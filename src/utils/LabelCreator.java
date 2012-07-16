package utils;

public class LabelCreator {
	private int labelIndex;
	private StringBuffer out;
	public LabelCreator(StringBuffer out)
	{
		this.out = out;
		labelIndex = 0;		
	}
	
	public void writeLabel()
	{
    	out.append("#" + labelIndex + ":");
    	out.append("\n");		
	}
	
	public void writeLabel(String label)
	{
    	out.append(label + labelIndex + ":");
    	out.append("\n");		
	}
	
	private void next()
	{
		labelIndex++;
	}
}
