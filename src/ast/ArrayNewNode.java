package ast;

import it.m2j.IdType;
import compiler.*;
//import utils.ParamUtils;


public class ArrayNewNode extends ExprNode
{    
	private IdType type;	
	private ListNode listDim;
	
    public ArrayNewNode(IdType type, ListNode listDim, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.type = type;
        this.listDim = listDim;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }
    
    public Object visitDim(Visitor v)
    {
    	return this.listDim.accept(v);
    }

    public String toString()
    {
        return "new " + type + this.listDim;
    }

    public int getDimension()
    {
    	return this.listDim.size();    	
    }
    
    public IdType getType()
    {
    	return this.type;
    } 
}