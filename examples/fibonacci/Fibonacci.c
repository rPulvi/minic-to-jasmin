/* Calcolo della serie di Fibonacci con complessita' O(n) */
 
/*  Passo iniziale: i primi due valori sono 0 e 1:               *
 *  li stampa, e chiama con questi valori la funzione ricorsiva  */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/fiboRec" void fiboRec(int,int,int);

void main (){
    int LIMIT;
    LIMIT = 1000000; /* limite della serie */

    print ("\n");
    print("La serie di Fibonacci (limite = ");
    print(i2s(LIMIT));
    print(") e':\n\n");
    print("0\n1\n");
    fiboRec(0,1,LIMIT);

    print("\n");
}
