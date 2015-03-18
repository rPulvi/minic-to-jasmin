

# Introduction #

The symbol table is managed by the `SymbolTable` class in the `compiler` package.
The symbol table handles both variables and functions symbol, using the `SymbolDesc` class to describe details for each symbol.

The symbol table is composed by an HashMap:
```
HashMap<String,ArrayList<SymbolDesc> > storage = new HashMap<String,ArrayList<SymbolDesc> >();
```

A generic entry in the map is so made up by a String that represents the ID for the entry, and a list of symbol descriptor, that brings some information like the type of the symbol (int, float, etc.), the kind (variable/function), the number of the code block (for scoping purposes) and a list of parameters used by a function.

# Details #

The `SymbolTable` class exposes two main methods to add items in the collection:

  * `putVariable`
  * `putFunction`

and some methods to retrieve elements from the list:

  * `get`
  * `getSpecific`
  * `getVariableType`
  * `getFunctionType`
  * `getVarDesc`
  * `getFuncDesc`

The `getSpecific` method return a list of descriptors for the specific symbol either variable or function.

The `put...` methods uses the `getSpecific` method to check if a new Symbol can be added to the map.
To add a variable in the map, the `putVariable` method checks if the item exists or not. If it exists then the method check if the item exist in a specific block, because we can have variables with the same name (map ID) but in different scopes.

A function can exist only once.

Note that it's possible to have a function and a varible with the same name. In this case we'll have a single entry for the symbol with two descriptors: one for the variable and one for the function.

See example below:

In this example we have either a function and a variable called `plootoh`

```

extern "MinicLib/plootoh" float plootoh(int);

float main()
{
   int plootoh;
   int p;
   [...]

   return plootoh(p);
}
```

| **ID**   | **Descriptors**                                                       |
|:---------|:----------------------------------------------------------------------|
| plootoh    | INT| null | `IdType.VARIABLE` | 1 | 0 | "" |
|                 |FLOAT |`[INT,0]`| `IdType.FUNCTION` | 0 |- | MinicLib/plootoh |

where each descriptor is an istance of the class `SymbolDesc`.