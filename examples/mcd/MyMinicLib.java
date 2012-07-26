public class MyMinicLib
{	
	public static int mcd (int n, int m){
		
		int mcd,x;
		
		mcd = 1;
		x = 2;
		
		while ((x <= m) && (x <= n)){
			
			if (((m % x) == 0) && ((n % x) == 0)){
				mcd = mcd * x;
				m = m/x;
				n = n/x;
			}else{
				x = x+1;
			}
		}
		
		return mcd;
	}
	
	
	public static int mcd_euc (int n, int m)
	{
		while (m != n){
			if (m > n){
				if ((m % n) == 0){
					m = n;
				}
				else{
					m = m % n;
				}
			}
			else{
				if ( (n % m) == 0){
					n = m;
				}
				else{
					n = n % m;
				}
			}
		}
		return n;
	}
	
}