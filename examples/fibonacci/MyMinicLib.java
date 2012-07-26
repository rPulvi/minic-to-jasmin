public class MyMinicLib
{	                           
	public static void fiboRec ( int lastButOne, int last, int LIMIT)
	{
	    int newOne;

	    newOne = lastButOne + last;

	    if (newOne < LIMIT){
	        MinicLib.println(MinicLib.int2string(newOne));
	        fiboRec(last, newOne,LIMIT);
	    }
	}
}