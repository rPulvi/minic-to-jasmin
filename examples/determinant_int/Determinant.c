/* Calcola il determinante di una matrice quadrata.
   NB: matrice[riga][colonna]
*/

//prova commento //singola linea
/* calcola il determinante con il metodo di sarrus */
int sarrus(int[][] matrix, int size) {
	
    int k, pos, neg, det,i,j,l,m;
    bool b;
    float f;
    int f;
    det = 0;
    i = 0;
    string s;
    i = (int) s;
    i = (int)f;
    
    i = -5;
    i = +22;
    
    b = !(i>5  && det ==0);
    b = !f;
    
    while(b){
    	i = b;
    }
    
    while( i < (size )){        
        pos = 1;
        j = f;
        i = j+s;
        j = s;
        
        while( (j < (size )) ){        	
            k = (i+j) % size;
            pos = pos * matrix[j][k];
            j = j+1;
            j=f;
        }
//commento        
        det = det + pos;
        i = i+1;
    }

    l = 0;
    while( l < size){
        neg = 1;
        m = 0;
        while( m < size ){
            int j;
            j = size - 1 - m;
            k = (l + (size ) -j) % size;
            neg = neg * matrix[j][k];
            m = m+1;
        }
            
        det = det - neg;
        l = l+1;
    }
  
    return det;
}

/* calcola il determinante di una matrice 2x2 */
int bidim(int[][] matrix){
    int det;

    det = matrix[0.4][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    return det;
}


/* stampa il contenuto di una matrice */
void print_matrix(int[][] matrix, int size){
	print("Data la matrice (");
	print(i2s(size));
	print("*");
	print(i2s(size));
	print_string("):");
    int i,j;
    i = 0;
    while( i < (size)){
    	j = 0;
        while( j < (size )){
        	print("   ");
            print(i2s(matrix[i][j]));
            j = j+1;
        }
        print_string("");
        i = i+1;
    }
}


void main(){
    /* dichiarazioni */
    int det;
    int[][] matrix;
    int SIZE1;
    int SIZE2;
    float err;
    j = 5;

    SIZE1 = 2;
    SIZE2 = 3;
    matrix = new int[SIZE1][SIZE1];

    matrix[0][0] = 0;
    matrix[1][0] = 3;
    matrix[0][1] = 1;
    matrix[1][1] = -1;

    print_matrix(matrix, SIZE1);
    det = bidim(matrix);
    print("Il suo determinante e':  ");
    print_string(i2s(det));

    matrix = new int[SIZE2][SIZE2];

    matrix[0][0] = -2;
    matrix[0][1] = 1;
    matrix[0][2] = 5;
    matrix[1][0] = 3;
    matrix[1][1] = -1;
    matrix[1][2] = -1;
    matrix[2][0] = 0;
    matrix[2][1] = 1;
    matrix[2][2] = -1;

    print_matrix(matrix, SIZE2);

    if (SIZE2 == 3){
        //det = sarrus(matrix, SIZE2);
        err = main();
        print("Il suo determinante e':  ");
            print_string(i2s(det));
    }
    else{
    	print("Non posso usare Sarrus per una matrice ");
    	print(i2s(SIZE2));
    	print("*");
    	print(i2s(SIZE2));
    	print_string("!!!");
    }
}
