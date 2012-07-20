package ast;

import aux.IdType;
import compiler.Visitor;

public class NullNode extends ExprNode
{
    public NullNode()
    {
        super(-1, -1);
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }

    public String toString()
    {
        return "";
    }

    public IdType getType()
    {
        return IdType.NULL;
    }
}
