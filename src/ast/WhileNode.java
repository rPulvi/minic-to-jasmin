package ast;

import compiler.Visitor;

public class WhileNode extends Node{

    private ExprNode testExpr;
    private Node whileStmt;
    
	public WhileNode(ExprNode testExpr, BlockNode whileStmt, int lineNumber, int colNumber) 
	{
		super(lineNumber, colNumber);
		this.testExpr = testExpr;
		this.whileStmt = whileStmt;
	}

	public Object accept(Visitor v) {

		return v.visit(this);
	}

	public Object visitTest(Visitor v)
    {
        return testExpr.accept(v);
    }

    public Object visitWhile(Visitor v)
    {
        return whileStmt.accept(v);
    }
    
    public String toString()
    {
    	return "while (" + testExpr + ")";
    }
}
