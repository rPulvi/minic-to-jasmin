extern "MyMinicLib/Funzione" int Funzione();

int main(){
	string c;
	
	Funzione();
	Funzione(4); 	//error
	
	c = Funzione(); //error
	
	//error - missing return type
}
