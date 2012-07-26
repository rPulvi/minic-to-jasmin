public class MyMinicLib
{	
	public static void swap (int[] A, int x, int y){
		
		int tmp;
		tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
	}
	
	
	public static void heapify (int[] A, int i, int r){
		
		int k;
		
		if ( r >= 2*(i+1)-1 ){
			if ( r > 2*(i+1)-1 ){
				if ( A[2*(i+1)-1] >= A[2*(i+1)] ){
					k = 2*(i+1)-1;
				}
				else{
					k = 2*(i+1);
				}
			}
			else{
				k = 2*(i+1)-1;
			}
			
			if ( A[k] > A[i] ){
				swap (A,k,i);
				heapify (A,k,r);
			}
		}
	}
	
	public static void buildheap (int[] A, int r){
		
		int k,i;
		i = 0;
		
		while(i <= (r/2)){
			k = r/2-i;
			heapify (A,k,r);
			i = i + 1;
		}
	}
	
	
	public static void heapsort (int r, int[] A){
		
		int b,i;
		int max;
		max = r;
		
		buildheap (A,r);
		i=0;
		while (i < max){
			b = max - i;
			swap (A,0,b);
			r = r-1;
			heapify (A,0,r);
			i = i + 1;
		}
	}
	
	
	
	public static void print_vector (int[] A, int size){
		
		int i;
		i = 0;
		while (i <= (size - 1)){
			MinicLib.print(MinicLib.int2string(A[i]));
			MinicLib.print("\t");
			i = i + 1;
		}
		
		MinicLib.println("\n");
	}
}