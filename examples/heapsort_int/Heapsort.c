/* Ordinamento di un vettore di interi tramite algoritmo Heapsort */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/swap" void swap(int[],int,int);
extern "MyMinicLib/heapify" void heapify(int[],int,int);
extern "MyMinicLib/buildheap" void buildheap(int[],int);
extern "MyMinicLib/heapsort" void heapsort(int[],int);
extern "MyMinicLib/print_vector" void print_vector(int[],int);

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
