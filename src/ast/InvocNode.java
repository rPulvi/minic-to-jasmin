package ast;

import compiler.Visitor;

public interface InvocNode
{
    Object[] visitParams(Visitor v);
    Node[] getParams();
    int getNumberOfParams();
}