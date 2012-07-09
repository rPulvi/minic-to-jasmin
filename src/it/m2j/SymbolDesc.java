package it.m2j;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>Title: MiniC to Jasmin</p>
 * <p>Description: a MiniC to Jasmin Compiler developed for the "Progetto di Compilatori e interpreti" course at the Universitˆ degli studi di Catania</p>
 * <p>Website: http://code.google.com/p/minic-to-jasmin/ </p>
 * @author Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravidˆ
 * @version 1.0
 */

public class SymbolDesc{
    private IdType type;
    private IdType kind;
    private int nBlock;
    private ArrayList<IdType> paramList;
    private int dim;
    private int jvmVar;
    
    public SymbolDesc(){
    	this.type = IdType.VOID;
    	this.kind = IdType.VOID; 
    	this.nBlock = 0;
    	this.paramList = null;
    	this.jvmVar = 0;
    }    
    
	/**
	 * This method creates a specific descriptor for a given variable
	 * @param type the type of the specific entry.
	 * @param dim the dimension of the specific entry. A value greater than zero means array.
	 * @param block the block number.
	 */
    public void setVariableSymbol(IdType type, int dim, int block){
        this.type = type;
        this.nBlock = block;
        this.kind = IdType.VARIABLE;
        this.dim = dim;
    }   
    
    /**
     * This method creates a specific descriptor for a given function
     * @param type the return type of the specific entry.
     * @param typeList the arguments of the function
     * @param dim indicates the return value dimension. A value greater than zero means array.
     */
    public void setFunctionSymbol(IdType type, ArrayList<IdType> typeList, int dim){
        this.type = type;
        this.dim = dim;
        this.paramList = new ArrayList<IdType>();
        
        if(typeList!=null)
        	this.paramList = (ArrayList<IdType>) typeList.clone();
        
        this.kind = IdType.FUNCTION;        
    }        

    /**
     * Set the type of a specific entry.
     * @param t the type.
     */
    public void setType(IdType t){
    	this.type = t;
    }

    /**
     * Get the type of a specific entry.
     * @return the type.
     */
    public IdType getType(){
        return type;
    }

    /**
     * Return the block number for a specific entry
     * @return the block Number.
     */
    public int getBlock(){
    	return nBlock;
    }
    
    /**
     * Return params list
     * @return the params as type list.
     */
    public ArrayList<IdType> getParamList(){
    	return this.paramList;
    }

    /**
     * Return if an entry is relatedo to a Function or a Variable.
     * @return the kind of a specific entry.
     */
    public IdType getKind(){
    	return this.kind;
    }
    
    public boolean checkType(SymbolDesc sym){
        if (sym.type == type)
            return true;
        else
            return false;
    }
    
    /**
     * Set an integer value that map a specific entry for the jvm code generation.
     * @param var the jvm representation of the variable.
     */
    public void setJvmVar(int var)
    {
    	this.jvmVar = var;
    }
    
    /**
     * Get an integer value that map a specific entry for the jvm code generation.
     * @return the jvm representation of the variable.
     */
    public int getJvmVar()
    {
    	return this.jvmVar;
    }
    
    /**
     * Return the dimension for a specific entry. Value greater than zero means array.
     * @return the dimension.
     */
    public int getDim()
    {
    	return this.dim;
    }
}
