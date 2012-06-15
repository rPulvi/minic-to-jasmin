package it.m2j;


class SymbolType{
    private IdType type;
    private int dim;

    SymbolType(IdType type){
        this.type = type;
        
        if (type!=IdType.VOID)
        	this.dim = 1;
        else
        	this.dim = -1;
    }    
    
    SymbolType(IdType type, int dim){
        this.type = type;
        this.dim = dim;
    }

    public IdType getType(){
        return type;
    }
    public int getDim(){
        return dim;
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
