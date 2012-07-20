package compiler;

import java.util.*;
import m2j.*;
import aux.*;

/**
 * <p>Title: MiniC to Jasmin</p>
 * <p>Description: a MiniC to Jasmin Compiler developed for the "Progetto di Compilatori e interpreti" course at the Universit� degli studi di Catania</p>
 * <p>Website: http://code.google.com/p/minic-to-jasmin/ </p>
 * @author Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravid�
 * @version 1.0
 */

public class SymbolTable
{
    //storage for the entries
    HashMap<String,ArrayList<SymbolDesc> > storage = new HashMap<String,ArrayList<SymbolDesc> >();    

    /**
     * Puts a variable in the symbol table.
     * @param id the name of the variable
     * @param symbolDesc the variable descriptor
     * @return true if the variable can be added. False otherwise.
     */
    public boolean putVariable(String id, SymbolDesc symbolDesc)
    {
    	return putVariable(id, symbolDesc.getType(), symbolDesc.getBlock(), symbolDesc.getDim(), symbolDesc.getJvmVar());
    }
    
    /**
     * Puts a variable in the symbol table.
     * @param id the name of the variable.
     * @param type the type of the variable.
     * @param blockNumber the block where the variable is declared.
     * @param dim the dimension of the variable. A value greater than zero means array.
     * @return true if the variable can be added. False otherwise.
     */
    public boolean putVariable(String id, IdType type, int blockNumber, int dim, int jvmName)
    {    	 
    	boolean bRet = false;
    	ArrayList<SymbolDesc> varDesc = this.getSpecific(id, IdType.VARIABLE);        	
    	
    	if (varDesc.size() == 0) //create a new entry in the table
    	{    		
        	ArrayList<SymbolDesc> descList = new ArrayList<SymbolDesc>();
        	SymbolDesc symbol = new SymbolDesc();

        	symbol.setVariableSymbol(type, dim, blockNumber, jvmName);

        	descList.add(symbol);

        	storage.put(id, descList);
        	
        	bRet = true;
    	}
    	else
    	{
    		boolean blockFound = false;
    		Iterator<SymbolDesc> it = varDesc.listIterator();
		    
			while (it.hasNext()) {
				SymbolDesc s = it.next();
			      
			      if (s.getBlock() == blockNumber){
			      	blockFound = true;	//Variable found in block
			      	bRet = false;
			      	break;
			      }		   
			}

		    if(blockFound == false) //Block not found - Add variable for the specific block
			{
		    	SymbolDesc symbol = new SymbolDesc();
		    	symbol.setVariableSymbol(type, dim, blockNumber, jvmName);
		    	this.get(id).add(symbol);
		    	bRet = true;
			}
    	}

    	return bRet;
    }
    
    /**
     * Puts a function in the symbol table.
     * @param id the name of the function.
     * @param symbolDesc the descriptor of the function.
     * @return true if the function can be added. False otherwise.
     */
    public boolean putFunction(String id, SymbolDesc symbolDesc)
    {
    	return putFunction(id, symbolDesc.getClassName(), symbolDesc.getType(),symbolDesc.getParamList(), symbolDesc.getDim());
    }
    
    public boolean putFunction(String id, String className, IdType type, ArrayList<NodeInfo> params, int dim)
    {
    	boolean bRet = false;
    	ArrayList<SymbolDesc> funDesc = this.getSpecific(id, IdType.FUNCTION);
    	
    	if (funDesc.size() == 0) //create a new entry in the table
    	{
	    	SymbolDesc symbol = new SymbolDesc();
	    	ArrayList<SymbolDesc> descList = new ArrayList<SymbolDesc>();    	

	    	symbol.setFunctionSymbol(type, params, dim, className);
	
	    	descList.add(symbol);
	
	    	storage.put(id, descList);
	    	
	    	bRet = true;
    	}
    	
    	return bRet;
    }

    /**
     * get an Array of Symbols from the symbol table for the specified identifier
     * @param key the symbol's key
     * @return null if the symbol is not in the symbol table
     */
    public ArrayList<SymbolDesc> get(String key)
    {    	    	    	    	
    	return storage.get(key);    	
    }    
        
    /**
     * get symbol descriptors from the symbol table for a specific kind
     * @param key the symbol's key
     * @return symbol descriptors
     */
    public ArrayList<SymbolDesc> getSpecific(String key, IdType kind)
    {
    	ArrayList<SymbolDesc> tmpDesc = new ArrayList<SymbolDesc>();
    	ArrayList<SymbolDesc> varDesc;
    	
    	varDesc = storage.get(key);
    	
        if (varDesc != null){ //Identifier found
			
        	Iterator<SymbolDesc> it = varDesc.listIterator();			
			
			while (it.hasNext()) {
				SymbolDesc s = it.next();

				if(s.getKind() == kind)
					tmpDesc.add(s);
			}
		}

        return tmpDesc;
    }  

    public IdType getVariableType(String key, int block)
    {
    	IdType typeRet = IdType.ERR;
    	
//    	SymbolDesc mainBlock;
//    	SymbolDesc specificBlock;
//    	
//    	mainBlock = getVarDesc(key, 1);
//
//    	if(mainBlock != null)
//    	{
//	    	specificBlock = getVarDesc(key, block);
//	    	
//			if (specificBlock == null)
//				typeRet = mainBlock.getType();
//			else
//				typeRet = specificBlock.getType();
//    	}

    	SymbolDesc varDesc = getVarDesc(key,block);
    	
    	if (varDesc == null)
    		typeRet = IdType.ERR;
    	else
    		typeRet = varDesc.getType();
    	
    	return typeRet;
    }
    
    public IdType getFunctionType(String key)
    {
    	ArrayList<SymbolDesc> varDesc = new ArrayList<SymbolDesc>();
    	IdType typeRet = IdType.ERR;
    	
    	varDesc = getSpecific(key, IdType.FUNCTION);
    	
    	if (varDesc.size() > 0)
    		typeRet = varDesc.get(0).getType();

    	return typeRet;
    }    
    
    public SymbolDesc getVarDesc(String key, int blockNumber)
    {
    	SymbolDesc retDesc = null;
    	
    	retDesc = lookupVar(key, blockNumber, IdType.VARIABLE);

    	if(retDesc == null)
    		retDesc = lookupVar(key, 1, IdType.VARIABLE);
    	
    	return retDesc;
    	
    }
    
    public SymbolDesc getFuncDesc(String key)
    {
    	SymbolDesc retDesc = null;
    	
    	retDesc = lookupVar(key, 0, IdType.FUNCTION);
    	
    	return retDesc;
    }
    
    private SymbolDesc lookupVar(String key, int blockNumber, IdType type)
    {
    	SymbolDesc sdRet = null;
    	ArrayList<SymbolDesc> varDesc = get(key);
    	
    	if(varDesc != null) {
    		
	    	if(varDesc.size() > 0)
	    	{
	    		Iterator<SymbolDesc> it = varDesc.listIterator();			
				
				while (it.hasNext()) {
					SymbolDesc s = it.next();
					
					if(s.getBlock() == blockNumber && s.getKind() == type){
						sdRet = s;
						break;
					}
				}
	    	}
    	}
    	
    	return sdRet;
    }
    
    public int varCount()
    {
    	int nVars=0;

    	Iterator it = storage.keySet().iterator();
    	
    	while (it.hasNext())
    	{    	
    		String key = (String) it.next();    		
    		nVars += getSpecific(key, IdType.VARIABLE).size(); 
    	}
    	
    	return nVars;
    }
}