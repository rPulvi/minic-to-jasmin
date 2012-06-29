package ast;

import compiler.*;
//import utils.ParamUtils;


public class NewNode extends ExprNode implements InvocNode
{
    private String className;
    private ListNode params;

    //the Symbol table entry that is the constructor in the new statement
//    private MethodSymbol symbol = null;
//    private compiler.ClassSymbol classSymbol;

    public NewNode(String className, ListNode params, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);
        this.className = className;
        this.params = params;
    }

    public Object accept(Visitor v)
    {
        return v.visit(this);
    }
//    public String toString()
//    {
//        return "new " + className + "(" + ParamUtils.makeSourceList(params) + ")";
//    }
    public String getClassName()
    {
        return className;
    }

//    public void setSymbol(MethodSymbol cons)
//    {
//        symbol = cons;
//    }

    public Object[] visitParams(Visitor v)
    {
        if (params == null)
            return new Object[0];

        Node[] paramList = params.toArray();
        Object[] result = new Object[paramList.length];
        for (int i = 0; i < paramList.length; ++i)
            result[i] = paramList[i].accept(v);

        return result;
    }

    public Node[] getParams()
    {
        if (params == null)
            return new Node[0];

        return params.toArray();
    }

    public int getNumberOfParams()
    {
        if (params == null)
            return 0;

        return params.toArray().length;
    }

//    public String getType()
//    {
//        if (symbol == null)
//            return "unknown";
//        else
//            return symbol.getName();
//    }
//    public void setClassSymbol(compiler.ClassSymbol classSymbol)
//    {
//        this.classSymbol = classSymbol;
//    }
//    public compiler.ClassSymbol getClassSymbol()
//    {
//        return classSymbol;
//    }
//    public MethodSymbol getSymbol()
//    {
//        return symbol;
//    }
}