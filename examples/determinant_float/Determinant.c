extern "MinicLib/println" void print_string(string);
extern "MinicLib/print" void print(string);
extern "MinicLib/float2string" string f2s(float);
extern "MinicLib/int2string" string i2s(int);

extern "MyMinicLib/sarrus" float sarrus(float[][],int);
extern "MyMinicLib/bidim" float bidim(float[][]);
extern "MyMinicLib/print_matrix" void print_matrix(float[][],int);

void main(){
    float det;
    float[][] matrix;
    int SIZE1;
    int SIZE2;

    SIZE1 = 2;
    SIZE2 = 3;
    matrix = new float[SIZE1][SIZE1];

    matrix[0][0] = 0.0;
    matrix[1][0] = 3.3;
    matrix[0][1] = 1.1;
    matrix[1][1] = -1.1;
    print_matrix(matrix, SIZE1);
    det = bidim(matrix);
    print("Il suo determinante e':  ");
    print_string(f2s(det));

    matrix = new float[SIZE2][SIZE2];

    matrix[0][0] = -2.3;
    matrix[0][1] = 1.7;
    matrix[0][2] = 5.5;
    matrix[1][0] = 3.4;
    matrix[1][1] = -1.7;
    matrix[1][2] = -1.9;
    matrix[2][0] = 0.4;
    matrix[2][1] = 1.3;
    matrix[2][2] = -1.6;

    print_matrix(matrix, SIZE2);

    if (SIZE2 == 3){
        det = sarrus(matrix, SIZE2);
        print("Il suo determinante e':  ");
            print_string(f2s(det));
    }
    else{
    	print("Non posso usare Sarrus per una matrice ");
    	print(i2s(SIZE2));
    	print("*");
    	print(i2s(SIZE2));
    	print_string("!!!");
    }
}
