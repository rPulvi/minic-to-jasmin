package utils;

public class StuffCreator {
	
	private StuffCreator()
	{
		//So, the class is noninstantiable		
	}
	
	public static String getBrackets(int dim)
	{
		String sRet = "";	
		
		for(int i=0;i<dim;i++)
		{
			sRet += "[]";
		}
		
		return sRet;
	}
}
