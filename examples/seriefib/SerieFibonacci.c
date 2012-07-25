  /* Serie di Fibonacci */
  /*
     I numeri di Fibonacci sono definiti dalla ricorrenza:
     Fib(0) = 0,
     Fib(1) = 1,
     Fib(i) = Fib(i-1) + Fib(i-2)    per i>=2.
  */


/* NOTA: nel main viene invocata la funzione Fib prima della definizione.
 * minic suppone che l'invocazione sia corretta, salva la locazione della
 * supposizione e prosegue. Nel momento in cui la funzione e' definita si
 * ripassano tutti i punti lasciati in sospeso.
 * Ovviamente se Fib non venisse mai definita sarebbe errore.
 */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/Fib" int Fib(int);

void main(){

    int i;
    i = 15;
    print ("\nCalcolo della serie di Fibonacci per il numero ");
    print(i2s(i));
    print(".\n");
    print ("Risultato: ");
    print(i2s(Fib(i)));
    print(".\n\n");
}
