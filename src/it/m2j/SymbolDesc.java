package it.m2j;

import java.util.ArrayList;
import java.util.Collections;

public class SymbolDesc{
    private IdType type;
    private IdType kind;
    private int nBlock;
    private ArrayList<IdType> paramList;
        

    public SymbolDesc(){
    	this.type = IdType.VOID;
    	this.kind = IdType.VOID; 
    	this.nBlock = 0;
    	this.paramList = null;
    }    
    
    public void setVariableSymbol(IdType type, int block){
        this.type = type;
        this.nBlock = block;
        this.kind = IdType.VARIABLE;
    }   
    
    public void setFunctionSymbol(IdType type, ArrayList<IdType> typeList){
        this.type = type;
        this.paramList = new ArrayList<IdType>();
        
        if(typeList!=null)
        	this.paramList = (ArrayList<IdType>) typeList.clone();
        
        this.kind = IdType.FUNCTION;
    }        

    public void setType(IdType t){
    	this.type = t;
    }

    public IdType getType(){
        return type;
    }

    public int getBlock(){
    	return nBlock;
    }
    
    public ArrayList<IdType> getParamList(){
    	return this.paramList;
    }

    public IdType getKind(){
    	return this.kind;
    }
    
    public boolean checkType(SymbolDesc sym){
        if (sym.type == type)
            return true;
        else
            return false;
    }
}
