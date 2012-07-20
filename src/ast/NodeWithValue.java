package ast;

import aux.IdType;
import compiler.Visitor;


public interface NodeWithValue
{
    public Object visitValue(Visitor v);
    public IdType getType();
    public String getName();
}