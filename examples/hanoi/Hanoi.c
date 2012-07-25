/* Soluzione del gioco della Torre di Hanoi */

/*numero di dischi utilizzati*/

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/hanoi" void hanoi(int,int,int);

void main(){
	int NDISK;
    NDISK = 5;
    print ("\nSoluzione della Torre di Hanoi con ");
    print( i2s(NDISK));
    print(" dischi.\n\n");
    hanoi(NDISK, 1, 3);
    print_string("");
}
