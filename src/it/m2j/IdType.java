package it.m2j;

public enum IdType {
	INT {		
		public String toString() 
		{
			return "int"; 
		}
	}, 
	FLOAT{
		public String toString() 
		{
			return "float"; 
		}
	}, 
	BOOL{
		public String toString() 
		{
			return "bool"; 
		}
	}, 
	STRING{
		public String toString() 
		{
			return "string"; 
		}
	}, 
	VOID{
		public String toString() 
		{
			return "void"; 
		}
	},
	ERR{		
		public String toString() 
		{
			return "error"; 
		}		
	},
	VARIABLE{
		public String toString()
		{
			return "Variable";
		}
	},
	FUNCTION{
		public String toString()
		{
			return "Function";
		}
	};	
	
	public boolean IsNumeric()
	{
		boolean bRet = false;
		
		if(this == INT || this == FLOAT) 
			bRet = true;
		
		return bRet;
	}
}
