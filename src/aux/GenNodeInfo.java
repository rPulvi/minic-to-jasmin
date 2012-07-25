package aux;

public class GenNodeInfo {
	private String name;
	private IdType kind;
	private Object value;
	private IdType type;
	private String jvmCode;
	private int dim;
	
	public GenNodeInfo(String name, IdType kind, Object value, IdType type, int dimension)
	{
		this.name = name;
		this.kind = kind;
		this.value = value;
		this.type = type;
		this.dim = dimension;
		this.jvmCode = "";
	}
	
	public GenNodeInfo(String name, IdType kind, String value, IdType type, int dimension, String jvmCode)
	{
		this.name = name;
		this.kind = kind;
		this.value = value;
		this.type = type;
		this.dim = dimension;
		this.jvmCode = jvmCode;
	}	
	
	public String getName()
	{
		return name;
	}
	
	public Object getValue()
	{
		return value;
	}
	
//	public String getValue()
//	{
//		return (String) value;
//	}
	
	public IdType getType()
	{
		return type;
	}
	
	public int getDim()
	{
		return dim;
	}
	
	public IdType getKind() {
		return kind;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setType(IdType type) {
		this.type = type;
	}
	
	public void setKind(IdType kind) {
		this.kind = kind;
	}
	
	public void setJvmCode(String jvmCode)
	{
		this.jvmCode = jvmCode;
	}
	
	public String getJvmCode()
	{
		return this.jvmCode;
	}
	
	public String toString() {
		return "Name:" + name + ", Kind:" + kind + ", Value:" + value + ", Type:" + type + ", Dim:" + dim;
	}
}
