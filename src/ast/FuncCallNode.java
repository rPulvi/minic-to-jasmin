package ast;

import compiler.*;
//import utils.ParamUtils;


public class FuncCallNode extends ExprNode
{    
    private String functionName;
    private ListNode params;

    //the static method to call
    //private MethodSymbol symbol = null;

    public FuncCallNode(String functionName, ListNode params, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);        
        this.functionName = functionName;
        this.params = params;
    }

    public FuncCallNode(String functionName, int lineNumber, int colNumber)
    {
        super(lineNumber, colNumber);        
        this.functionName = functionName;
        this.params = null; //No Params
    }
    
    public Object accept(Visitor v)
    {
        return v.visit(this);
    }
    public String toString()
    {    	
    	return functionName + "(" + params +")";
    }

    public String getName()
    {
        return functionName;
    }

//    public void setSymbol(MethodSymbol symbol)
//    {
//        this.symbol = symbol;
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
    
//    public ListNode getParams()
//    {
//    	return this.params;
//    }
    
    public int getNumberOfParams()
    {
        if (params == null)
            return 0;

        return params.toArray().length;
    }

//    public MethodSymbol getSymbol()
//    {
//        return symbol;
//    }
}