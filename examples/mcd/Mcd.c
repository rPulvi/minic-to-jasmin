/* Calcola il Massimo Comun Divisore fra due numeri */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

int mcd (int n, int m){

	int mcd,x;

    mcd = 1;
    x = 2;
    
    while ((x <= m) && (x <= n)){

    	if (((m % x) == 0) && ((n % x) == 0)){
		    mcd = mcd * x;
		    m = m/x;
            n = n/x;
        }else{
            x = x+1;
        }
    }

    return mcd;
}


int mcd_euc (int n, int m)
{
    while (m != n){
	    if (m > n){
		    if ((m % n) == 0){
                m = n;
            }
		    else{
                m = m % n;
            }
        }
	    else{
		    if ( (n % m) == 0){
                n = m;
            }
		    else{
                n = n % m;
            }
        }
	}
    return n;
}


void main (void)
{
    int i,j;
    
    i = 1152;
    j = 480;
    
    print ("\nCalcolo l'MCD fra ");
    print (i2s(i));
    print(" e ");
    print(i2s(j));
    print(".\n");
    
    print ("\nIl risultato e': ");
    print(i2s(mcd(i,j)));
    
    print ("\nIl risultato usando il metodo di Euclide e': ");
    print(i2s(mcd(i,j)));
    print("\n\n");
}
