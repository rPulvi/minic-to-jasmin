package utils;

import aux.IdType;

public class StuffCreator {
	
	private StuffCreator()
	{
		//So, the class is noninstantiable		
	}
	
	public static String getBrackets(int dim)
	{
		String sRet = "";	
		
		for(int i=0;i<dim;i++)
			sRet += "[]";
		
		return sRet;
	}
	
	public static String getOpenBrackets(int dim)
	{
		String sRet = "";	
		
		for(int i=0;i<dim;i++)
			sRet += "[";
		
		return sRet;
	}
	
    public static String getJVMType(IdType t)
    {
    	switch(t)
		{
			case INT:
				return "I";
			case FLOAT:
				return "F";
			case STRING:
				return "Ljava/lang/String;";
			case BOOL:
				return "I";
			case VOID:
				return "V";
			default:
				return "V";
		}
    }      
}
