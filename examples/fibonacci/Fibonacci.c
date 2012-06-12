/* Calcolo della serie di Fibonacci con complessita' O(n) */
 
/*  Passo iniziale: i primi due valori sono 0 e 1:               *
 *  li stampa, e chiama con questi valori la funzione ricorsiva  */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

void main (void){
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


/*  Stampa la serie di Fibonacci fino a un certo valore limite.  
   Usa una funzione ricorsiva, che dati due valori, trova il    
   successivo, e invoca nuovamente la funzione con gli ultimi  
   due valori trovati                                           */
void fiboRec ( int lastButOne, int last, int LIMIT){
    int newOne;

    newOne = lastButOne + last;

    if (newOne < LIMIT){
        print_string (i2s(newOne));
        fiboRec(last, newOne,LIMIT);
    }
}
