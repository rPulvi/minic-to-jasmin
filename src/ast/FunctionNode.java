package ast;

import it.m2j.IdType;
import compiler.*;


public class FunctionNode extends SlotNode
{
    protected ListNode params;
    private BlockNode body;

    //private MethodSymbol symbol = null;

    public FunctionNode(String name, ListNode params, IdType type, BlockNode body, int lineNumber, int colNumber)
    {
        super(name, type, lineNumber, colNumber);
        this.params = params;
        this.body = body;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public void visitParams(Visitor v)
    {
        if (params != null)
            params.accept(v);
    }

//    public String toString()
//    {
//        return access + " " + name + "(" + ParamUtils.makeSourceList(params) + ")";
//    }

    public void visitBody(Visitor v)
    {    	
        if (body != null)
            body.accept(v);
    }

    public IdType getType()
    {
        return type;
    }

    public void setParams(ListNode params)
    {
        this.params = params;
    }

//    public void setSymbol(MethodSymbol symbol)
//    {
//        this.symbol = symbol;
//    }
//
//    public MethodSymbol getSymbol()
//    {
//        return symbol;
//    }
}