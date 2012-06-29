package ast;

import compiler.Visitor;
import java.util.Vector;

public final class ListNode extends Node
{
    //the left child of the node in the AST
    protected Node left;
    //the right child of the node in the AST
    protected Node right;

    public ListNode(Node left, Node right)
    {
        super(0, 0);
        this.left = left;
        this.right = right;
    }

    public void visitChildren(Visitor v)
    {
        if (left != null)
            left.accept(v);
        if (right != null)
            right.accept(v);
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        if (left == null && right == null)
            return "";
        if (left == null)
            return right.toString();
        if (right == null)
            return left.toString();

        return left + "\n" + right;
    }

    public Node[] toArray()
    {
        if (left == null && right == null)
            return new Node[0];

        Vector result = new Vector();

        if (left != null)
        {
            if (left instanceof ListNode)
            {
                Node[] leftResult = ((ListNode)left).toArray();
                for (int i = 0; i < leftResult.length; ++i)
                    result.add(leftResult[i]);
            }
            else
                result.add(left);
        }
        if (right != null)
        {
            if (right instanceof ListNode)
            {
                Node[] rightResult = ((ListNode)right).toArray();
                for (int i = 0; i < rightResult.length; ++i)
                    result.add(rightResult[i]);
            }
            else
                result.add(right);
        }

        return (Node[])result.toArray(new Node[0]);
    }

    public int size()
    {
        int result = 0;
        if (left != null)
        {
            if (left instanceof ListNode)
                result += ((ListNode)left).size();
            else
                ++result;
        }
        if (right != null)
        {
            if (right instanceof ListNode)
                result += ((ListNode)right).size();
            else
                ++result;
        }

        return result;
    }
}