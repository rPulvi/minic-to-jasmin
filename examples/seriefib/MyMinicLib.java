public class MyMinicLib
{	
	public static int Fib (int i){
		
		if (i == 0){
			
			return 0;
		}
		else{
			if (i == 1){
				return 1;
			}
			else{
				return (Fib(i-1) + Fib(i-2));
			}
		}
}
}