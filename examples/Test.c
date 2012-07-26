float[][] main() {
	
	float[][] matrix;
	//float[] array;
	//float f;
	
	matrix = new float[2][2];
	//array = new float[3];
	
	
	//array[0] = 1.1;
	//array[1] = 2.1;
	//array[2] = 3.1;
	
	
	matrix[0][0] = 1.1;
	matrix[0][1] = 2.1;
	matrix[1][0] = 3.1;
	matrix[1][1] = 4.1;
	
	//f = array[0] + array[1];
	//f = matrix[0][0] + matrix[0][1];
	
	//f=5+3.6+2*2; 	//funziona
	//f = 5.6+2*3; 	//non funzionava, ora funziona
	//f = (-((5*2)/4)+7.1-2*3)+2; //  ---> e funziona pure questo!!! :) :)
	//f = (-((5*2)/4)+7-2*3.2)+2; //  ---> questo non funziona per colpa del 3.2 e della solita conversione a float
	//f=(2*3.2+5+7.1+3)*2;
	
	return matrix;
}


//problema nelle promozioni di tipo delle espressioni (il problema è dovuto al fatto che
// non stampa ldc per il primo operando a sinistra dell'AddNode, perchè prima esegue la 
//visitBinary per la moltiplicazione)

//eliminare stampa di debug nel costruttore del BinaryNode
//Apparentemente sembra funzionare il problema delle promozioni di tipo nelle expr 
//(risolto con un if-else)


//int factorial(int n) {
//	if (n==0){
//		return 1;
//	}
//	else {
//		return n*factorial(n-1);	
//	}
//}


