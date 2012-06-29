package ast;

import compiler.*;


public class BlockNode extends Node
{
    private ListNode stmts;

    //private ScopeSymbol symbol = null;

    public BlockNode(ListNode stmts, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.stmts = stmts;
    }

    public String toString()
    {
        return "{\n" + stmts + "\n}";
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public void visitChildren(Visitor v)
    {
        if (stmts != null)
            stmts.accept(v);
    }

    /*
    public void setSymbol(ScopeSymbol symbol)
    {
        this.symbol = symbol;
    }
    public ScopeSymbol getSymbol()
    {
        return symbol;
    }
    */
}
