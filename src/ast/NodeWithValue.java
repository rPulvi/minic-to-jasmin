package ast;
import it.m2j.IdType;
import compiler.Visitor;


public interface NodeWithValue
{
    public Object visitValue(Visitor v);
    public IdType getType();
    public String getName();
}