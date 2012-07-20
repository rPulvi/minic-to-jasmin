package ast;

import aux.IdType;
import compiler.Visitor;

public class FunctionExtNode extends SlotNode{
	
	protected ListNode params;
	private int dim;
	private String className;
	
	public FunctionExtNode(String name, IdType type, String path, int dim, ListNode params, int lineNumber, int colNumber) {
		super(name, type, lineNumber, colNumber);
		this.params = params;
		this.dim = dim;
		this.className = path;
	}

	public Object accept(Visitor v) {
		
		return v.visit(this);
	}

    public void setParams(ListNode params)
    {
        this.params = params;
    }
    
//    public Node[] getParams()
//    {
//        if (params == null)
//            return new Node[0];
//
//        return params.toArray();
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
    
    public IdType getType()
    {
        return type;
    }
    
    public int getDimension()
    {
    	return this.dim;
    }
    
    public String getClassName()
    {
    	return this.className;
    }
}
