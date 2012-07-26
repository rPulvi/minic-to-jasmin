/* Ordinamento di un vettore di interi tramite algoritmo Quicksort */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/swap" void swap(int[],int,int);
extern "MyMinicLib/quick" void quick(int,int[],int);
extern "MyMinicLib/print_vector" void print_vector(int[],int);

void main()
{
    /* dichiarazioni */
    int[] vect;
    int SIZE;
    /* inizializzazioni */
    SIZE = 10;
    vect = new int[SIZE];

    /* riempio l'array */
    vect[0] = 3;  vect[5] = 9;
    vect[1] = 5;  vect[6] = 3;
    vect[2] = -7; vect[7] = -4;
    vect[3] = 1;  vect[8] = 8;
    vect[4] = 0;  vect[9] = 6;

    print("\nIl vettore iniziale e':\n\n");
    print_vector(vect, SIZE);

    quick(SIZE - 1, vect, 0);

    print("\nIl vettore ordinato tramite Quicksort e':\n\n");
    print_vector(vect, SIZE);
}
