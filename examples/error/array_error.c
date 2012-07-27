void main(){
	
	int[][] a;
	int[] b;
	int c;
	
	float[] d;
	bool[] e;
	string[] f;

	d = new float[3];
	e = new bool[2];
	a = new int[2][3];
	b = new int[3.2];	//error
	f = new string[3];
	

	b = a[0];
	f[0] = "prova";
		
	e = d;			//error
	b = c[1];		//error
	a = b[0][0];	//error
}
