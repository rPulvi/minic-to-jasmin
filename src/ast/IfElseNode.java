package ast;

import compiler.Visitor;


public class IfElseNode extends Node
{
    private ExprNode testExpr;
    private Node thenStmt;
    private Node elseStmt;

    public IfElseNode(ExprNode testExpr, BlockNode thenStmt, BlockNode elseStmt, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.testExpr = testExpr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public Object visitTest(Visitor v)
    {
        return testExpr.accept(v);
    }

    public Object visitThen(Visitor v)
    {
        return thenStmt.accept(v);
    }
    
    public Object visitElse(Visitor v)
    {
    	return elseStmt.accept(v);
    }
    
    public String toString()
    {
        return "if (" + testExpr + ")";
    }
}