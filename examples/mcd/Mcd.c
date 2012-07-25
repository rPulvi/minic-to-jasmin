/* Calcola il Massimo Comun Divisore fra due numeri */

extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/mcd" int mcd(int,int);
extern "MyMinicLib/mcd_euc" int mcd_euc(int,int);

void main()
{
    int i;
    int j;
    
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
