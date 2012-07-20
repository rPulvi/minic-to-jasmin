package aux;

public class GenNodeInfo {
	private String name;
	private IdType kind;
	private String value;
	private IdType type;
	private int dim;
	
	public GenNodeInfo(String name, IdType kind, String value, IdType type, int dimension)
	{
		this.name = name;
		this.kind = kind;
		this.value = value;
		this.type = type;
		this.dim = dimension;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getValue()
	{
		return value;
	}
	
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
	
	public String toString() {
		return "Name:" + name + ", Kind:" + kind + ", Value:" + value + ", Type:" + type + ", Dim:" + dim;
	}
}
