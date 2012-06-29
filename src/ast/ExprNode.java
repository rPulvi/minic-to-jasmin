package ast;


public abstract class ExprNode extends Node
{
    ExprNode(int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
    }
}