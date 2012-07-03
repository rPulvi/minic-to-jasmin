package ast;

import compiler.*;


public class DeclNode extends ExprNode
{

    private VarNode vNode;

    //private VariableSymbol symbol = null;

    public DeclNode(VarNode e, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        vNode = e;
    }   
    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public VarNode getVar()
    {
    	return vNode;
    }
    
    public String toString()
    {
        return vNode.toString();
    }
}