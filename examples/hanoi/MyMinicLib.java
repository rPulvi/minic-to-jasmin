public class MyMinicLib
{	                           
	public static void hanoi( int n, int peg1, int peg2){
		if (n > 0){
			hanoi(n-1, peg1, 6-peg1-peg2);
			MinicLib.print ("Muovi l'anello ");
			MinicLib.print( MinicLib.int2string(n));
			MinicLib.print(" dal piolo ");
			MinicLib.print(MinicLib.int2string(peg1));
			MinicLib.print(" al piolo ");
			MinicLib.print(MinicLib.int2string(peg2));
			MinicLib.print(".\n");
			hanoi(n-1, 6-peg1-peg2, peg2);
		}
	}
}