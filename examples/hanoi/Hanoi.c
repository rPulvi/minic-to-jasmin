/* Soluzione del gioco della Torre di Hanoi */

/*numero di dischi utilizzati*/

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

void hanoi( int n, int peg1, int peg2){
	if (n > 0){
        float f;
        hanoi(n-1, peg1, 6-peg1-peg2);
        print ("Muovi l'anello ");
        print( i2s(f));
        print(" dal piolo ");
        print(i2s(peg1));
        print(" al piolo ");
        print(i2s(peg2));
        print(".\n");
        hanoi(n-1, 6-peg1-peg2, peg2);
    }
}


void main(){
	int NDISK;
    NDISK = 5;
    print ("\nSoluzione della Torre di Hanoi con ");
    print( i2s(NDISK));
    print(" dischi.\n\n");
    hanoi(NDISK, 1, 3);
    print_string("");
}
