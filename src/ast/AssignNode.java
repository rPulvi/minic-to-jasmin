package ast;

import compiler.Visitor;


public class AssignNode extends ExprNode
{
    private ExprNode var;
    private ExprNode value;   
    
    public AssignNode(ExprNode var, ExprNode e, int block, int lineNumber, int colNumber)
    {    	
        super(lineNumber, colNumber);
        
        this.var = var;
        this.blockNumber = block;
        this.value = e;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return var + " = " + value;
    }

    public Object visitValue(Visitor v)
    {    	
        return value.accept(v);
    }
    
    public Object visitVar(Visitor v)
    {
    	return var.accept(v);
    }    
}