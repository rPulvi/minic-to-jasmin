package it.m2j;


class SymbolType{
    private IdType type;
    private int dim;
    private int nBlock;

    SymbolType(IdType type){
        this.type = type;
        
        if (type!=IdType.VOID)
        	this.dim = 1;
        else
        	this.dim = -1;
    }    
    
    SymbolType(IdType type, int block){
        this.type = type;
        this.nBlock = block;
        this.dim = -1;
    }

    public IdType getType(){
        return type;
    }
    public void setType(IdType t){
    	this.type = t;
    }
    
    public int getDim(){
        return dim;
    }
    public int getBlock(){
    	return nBlock;
    }

    public boolean checkType(SymbolType sym){
        if (sym.type == type)
            return true;
        else
            return false;
    }

    public boolean checkDim(SymbolType sym){
        if (sym.dim < dim)
            return true;
        else
            return false;
    }
}
