package it.m2j;

import java.util.ArrayList;
import java.util.Collections;


class SymbolType{
    private IdType type;    
    private int nBlock;
    private ArrayList<IdType> paramList;
    
    SymbolType(IdType type){
        this.type = type;         
    }    
    
    //Used by variables
    SymbolType(IdType type, int block){
        this.type = type;
        this.nBlock = block;
    }
    
    //Used by functions
    SymbolType(IdType type, ArrayList<IdType> typeList){
        this.type = type;
        this.paramList = new ArrayList<IdType>();
        
        if(typeList!=null)
        	this.paramList = (ArrayList<IdType>) typeList.clone();
    }

    public IdType getType(){
        return type;
    }
    public void setType(IdType t){
    	this.type = t;
    }
    
    public int getBlock(){
    	return nBlock;
    }
    
    public ArrayList<IdType> getParamList(){
    	return this.paramList;
    }

    public boolean checkType(SymbolType sym){
        if (sym.type == type)
            return true;
        else
            return false;
    }
}
