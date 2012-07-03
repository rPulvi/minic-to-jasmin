package ast;


public abstract class ExprNode extends Node
{
    ExprNode(int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
    }
    
    ExprNode(int blockNumber, int lineNumber, int colNumber)
    {
        super(blockNumber, lineNumber, colNumber);
    }
}