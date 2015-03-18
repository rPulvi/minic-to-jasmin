# Introduction #

Each symbol in the symbol table is made up an ID and a list of _descriptors_ that describes each entry.
The descriptors are used to distinguish between variables and functions entry in the symbol table, because it's possible to have a variable and a fuction with the same name in the table

# Details #

Each descriptor is made up of this fields:

```

public class SymbolDesc{
    private IdType type;
    private IdType kind;
    private int nBlock;
    private ArrayList<IdType> paramList;
    private int dim;
    private int jvmVar;
    private String className;

   [...]
```

| Field | Desc |
|:------|:-----|
| `type` | speciify the id type (eg: INT, FLOAT...). |
| `kind` | if the descriptor refers to a FUNCTION or a VARIABLE. |
| `nBlock` | the number of the block where the variable is declared. Used only for variables. |
| `paramList` | the list of the types of the params. Used only for functions. |
| `dim` | the dimension of the variable. It is used to distinguish between normal var and arrays. |
| `jvmVar` | the var identifier for jvm |
| `className` | the name of a function including its class name (if extern). |

# Methods #

Main methods are:

`  public void setVariableSymbol(IdType type, int dim, int block) [...] `

creates a descriptor for a variable in the map.

` public void setFunctionSymbol(IdType type, ArrayList<IdType> typeList, int dim) [...] `

creates a descriptor for a function in the map.