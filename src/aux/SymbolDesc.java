package aux;

import java.util.ArrayList;
import java.util.Iterator;
import utils.StuffCreator;

/**
 * <p>Title: MiniC to Jasmin</p>
 * <p>Description: a MiniC to Jasmin Compiler developed for the "Progetto di Compilatori e interpreti" course at the Universita' degli studi di Catania</p>
 * <p>Website: http://code.google.com/p/minic-to-jasmin/ </p>
 * @author Alessandro Nicolosi, Riccardo Pulvirenti, Giuseppe Ravida'
 * @version 1.0
 */

public class SymbolDesc{
    private IdType type;
    private IdType kind;
    private int nBlock;
    private ArrayList<NodeInfo> paramList;
    private int dim;
    private int jvmVar;
    private String className;
    
    public SymbolDesc(){
    	this.type = IdType.VOID;
    	this.kind = IdType.VOID; 
    	this.nBlock = 0;
    	this.paramList = null;
    	this.jvmVar = 0;
    	this.className = "";
    }    
    
	/**
	 * This method creates a specific descriptor for a given variable
	 * @param type the type of the specific entry.
	 * @param dim the dimension of the specific entry. A value greater than zero means array.
	 * @param block the block number.
	 */
    public void setVariableSymbol(IdType type, int dim, int block, int jvmName){
        this.type = type;
        this.nBlock = block;
        this.kind = IdType.VARIABLE;
        this.dim = dim;
        this.jvmVar = jvmName;
    }   
    
    /**
     * This method creates a specific descriptor for a given function
     * @param type the return type of the specific entry.
     * @param typeList the arguments of the function
     * @param dim indicates the return value dimension. A value greater than zero means array.
     */
    public void setFunctionSymbol(IdType type, ArrayList<NodeInfo> typeList, int dim, String className){
        this.type = type;
        this.dim = dim;
        this.paramList = new ArrayList<NodeInfo>();
        
        if(typeList!=null)
        	this.paramList = (ArrayList<NodeInfo>) typeList.clone();
        
        this.kind = IdType.FUNCTION;
        this.className = className;
    }        

    /**
     * Sets the type of a specific entry.
     * @param t the type.
     */
    public void setType(IdType t){
    	this.type = t;
    }

    /**
     * Gets the type of a specific entry.
     * @return the type.
     */
    public IdType getType(){
        return type;
    }

    /**
     * Returns the block number for a specific entry
     * @return the block Number.
     */
    public int getBlock(){
    	return nBlock;
    }
    
    /**
     * Sets the className for a function. If extern, the className is the name of the class including the name of the function. Only the name of the funcion otherwise.
     * @param className
     */
    public void setClassName(String className)
	{
		this.className = className;
	}

    /**
     * Returns the className for a specific function.
     * @return
     */
    public String getClassName()
	{
		return this.className;
	}
    
    /**
     * Returns params list
     * @return the params as type list.
     */
    public ArrayList<NodeInfo> getParamList(){
    	return this.paramList;
    }

    /**
     * Returns if an entry is relatedo to a Function or a Variable.
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
     * Sets an integer value that map a specific entry for the jvm code generation.
     * @param var the jvm representation of the variable.
     */
    public void setJvmVar(int var)
    {
    	this.jvmVar = var;
    }
    
    /**
     * Gets an integer value that map a specific entry for the jvm code generation.
     * @return the jvm representation of the variable.
     */
    public int getJvmVar()
    {
    	return this.jvmVar;
    }
    
    /**
     * Returns the dimension for a specific entry. Value greater than zero means array.
     * @return the dimension.
     */
    public int getDim()
    {
    	return this.dim;
    }
    
    /**
     * Returns a a human readable String that list the parameters for a specific descriptor.
     * @return the string.
     */
    public String toString()
    {
    	if(kind == IdType.VARIABLE)
    		return "Type: " + type + ", Kind: " + kind + ", Block: " + ", Dimension: " + dim + ", JVM Name: " + jvmVar;
    	else
    		return "Type: " + type + ", Kind: " + kind + ", Block: " + ", Params: " + getParams() + ", Dimension: " + dim + ", JVM Name: " + jvmVar + ", Class Name: " + className;
    }
    
    /**
     * Returns the type of the pararameters in a function.
     * @return a string like "(type1 par1, type2[]..[] par2, ...., typen parn)"
     */
    private String getParams()
    {    	
    	String sRet = "(";
    	
    	Iterator<NodeInfo> it = paramList.listIterator();
    	
    	while(it.hasNext())
    	{
    		NodeInfo info = it.next();
    		sRet += info.getType() + StuffCreator.getBrackets(info.getDim()) + ", " ;
    	}
    	
    	sRet += ")";
    	
    	return sRet;
    }
}
