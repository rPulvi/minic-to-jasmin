package ast;

import it.m2j.IdType;
import compiler.*;


public class DeclNode extends ExprNode
{
    private String varName;
    private IdType type;
    private int block;

    //private VariableSymbol symbol = null;

    public DeclNode(String varName, IdType type, int blockNumber, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.varName = varName;
        this.type = type;
        this.block = blockNumber;
    }   
    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public IdType getType()
    {
        return type;
    }

    public String getName()
    {
        return varName;
    }

    public String toString()
    {
        return type.toString() + " " + varName;
    }
    
    public int getBlockNumber()
    {
    	return block;
    }
    /*
    public void setSymbol(VariableSymbol symbol)
    {
        this.symbol = symbol;
    }
    public VariableSymbol getSymbol()
    {
        return symbol;
    }
    */

}