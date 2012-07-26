public class MyMinicLib
{	
	public static void swap( int[] A, int x, int y)
	{
		int tmp;
		tmp = A[x];
		A[x] = A[y];
		A[y] = tmp;
	}
	
	public static void quick( int r, int[] A, int l)
	{
		int i, j;
		
		if (l < r)
		{
			i = l+1;
			j = r;
			while (i < j)
			{
				while ((A[i] <= A[l] && (i < j)))
				{
					i=i+1;
				}
				while ((A[j] >= A[l] && (i < j)))
				{
					j=j-1;
				}
				if (i < j)
				{
					swap(A, i, j);
				}
			}
			if (A[i] <= A[l])
			{
				swap(A, i, l);
			}
			else
			{
				i = i - 1;
				swap(A, i, l);
			}
			quick(i-1, A, l);
			quick(r, A, i+1);
		}
	}
	
	public static void print_vector( int[] A, int size)
	{
		int i;
		i = 0;
		
		while(i < size){
			MinicLib.print(MinicLib.int2string(A[i]));
			MinicLib.print("\t");
			i = i + 1;
		}
		MinicLib.print ("\n\n");
	}
}