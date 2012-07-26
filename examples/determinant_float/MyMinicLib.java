public class MyMinicLib
{	
	public static float sarrus(float[][] matrix, int size) {
		int k, i, j, l, m;
		float det, pos, neg;
		det = 0.0f;
		i = 0;
		while( i < (size )){
			pos = 1 + 0;
			j = 0;
			while( (j < (size )) ){
				k = (i+j) % size;
				pos = pos * matrix[j][k];
				j = j+1;
			}
			
			det = det + pos;
			i = i+1;
		}
		
		l = 0;
		while( l < (size)){
			neg = 1;
			m = 0;
			while( m < (size )){
				int x;
				x = size - 1 - m;
				k = (l + (size ) - x) % size;
				neg = neg * matrix[x][k];
				m = m+1;
			}
            
			det = det - neg;
			l = l+1;
		}
		return det;
	}
	
	public static float bidim(float[][] matrix){
		float det;
		
		det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		
		return det;
	}
	
	public static void print_matrix(float[][] matrix, int size){
		MinicLib.print("Data la matrice (");
		MinicLib.print(MinicLib.int2string(size));
		MinicLib.print("*");
		MinicLib.print(MinicLib.int2string(size));
		MinicLib.println("):");
		int i,j;
		i = 0;
		while( i < (size)){
    	 	j = 0;
			while( j < (size )){
				MinicLib.print("   ");
				MinicLib.print(MinicLib.float2string(matrix[i][j]));
				j = j+1;
			}
			MinicLib.println("");
			i = i+1;
		}
	}
}