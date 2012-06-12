extern "MinicLib/println" void print_string(string);
extern "MinicLib/int2string" string i2s(int);
extern "MinicLib/float2string" string f2s(float);

void main( void ){
	float[][] array;
	float result;
	array = new float[2][2];

	//Esempio di promoting semplice
	array[0][0] = 21;
	array[0][1] = 3;
	array[1][0] = 11;
	array[1][1] = 10;

	result = ( array[0][0] + array[0][1] + array[1][0]) /  array[1][1];
	print_string(f2s(result));


	int intero;
	intero = 3;

	float espressione_complessa;
	espressione_complessa = 12.2 + (9.3 * 11.2) + 4 - 12 + 22.3 + (34 / 10.2);
	int cast_val;

	//esempio di cast
	cast_val = (int) espressione_complessa;


	float float_promote;

	//esempio di promozione con prima somma non floating point
	float_promote = 15 + intero + espressione_complessa;


	print_string(i2s(intero));
	print_string(f2s(espressione_complessa));
	print_string(i2s(cast_val));
	print_string(f2s(float_promote));
}
