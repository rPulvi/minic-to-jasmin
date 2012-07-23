package ast;

import compiler.Visitor;


public abstract class BinaryNode extends ExprNode
{
    //the left child of the node in the AST
    protected Node left;
    //the right child of the node in the AST
    protected Node right;

    public BinaryNode(Node left, Node right, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.left = left;
        this.right = right;
        //System.out.println("Left ---> " + left + ", Right ---> " + right);
    }

    public Object visitLeft(Visitor v)
    {
        return left.accept(v);
    }

    public Object visitRight(Visitor v)
    {
        return right.accept(v);
    }
}