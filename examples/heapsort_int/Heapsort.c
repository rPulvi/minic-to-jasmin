/* Ordinamento di un vettore di interi tramite algoritmo Heapsort */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

void swap (int[] A, int x, int y){

    int tmp;
    tmp = A[x];
    A[x] = A[y];
    A[y] = tmp;
}


void heapify (int[] A, int i, int r){

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

void buildheap (int[] A, int r){

    int k,i;
    i = 0;
    
    while(i <= (r/2)){
        k = r/2-i;
        heapify (A,k,r);
        i = i + 1;
    }
}


void heapsort (int[] A, int r){

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

void main(){

	int size;
    size = 10;
    int[] vect;
    vect = new int[size];
    
    vect[0] = 95;
    vect[1] = 1;
    vect[2] = -2;
    vect[3] = -1;
    vect[4] = 1;
    vect[5] = 6;
    vect[6] = 12;
    vect[7] = -44;
    vect[8] = 7;
    vect[9] = 0;

    print_string("\nIl vettore iniziale e':\n");
    print_vector(vect, size);
    
    heapsort(vect, size - 1);

    print_string("\nIl vettore ordinato tramite Heapsort e':\n");
    print_vector(vect, size);
}


void print_vector (int[] A, int size){

	int i;
	i = 0;
    while (i <= (size - 1)){
    	print(i2s(A[i]));
        print("\t");
        i = i + 1;
    }

    print_string("\n");
}
