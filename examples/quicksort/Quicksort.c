/* Ordinamento di un vettore di interi tramite algoritmo Quicksort */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

void swap( int[] A, int x, int y)
{
    int tmp;
    tmp = A[x];
    A[x] = A[y];
    A[y] = tmp;
}


void quick( int[] A, int l, int r)
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
        quick(A, l, i-1);
        quick(A, i+1, r);
    }
}


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

    quick(vect, 0, SIZE - 1);

    print("\nIl vettore ordinato tramite Quicksort e':\n\n");
    print_vector(vect, SIZE);
}


void print_vector( int[] A, int size)
{
	int i;
	i = 0;

	while(i < size){
		print(i2s(A[i]));
		print("\t");
		i = i + 1;
	}
    print ("\n\n");
}
