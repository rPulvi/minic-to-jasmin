package it.m2j;

public class Location {
	
	private static YYLType yylloc;
	
	// Initialize location
	public static void locInit()
	{
	    yylloc.firstLine = 1;
	    yylloc.firstColumn = 0;
	    yylloc.lastLine = 1;
	    yylloc.lastColumn = 0;
	}

	// Set location with generic new token
	void locToken(int len)
	{
	    yylloc.firstLine = yylloc.lastLine;
	    yylloc.firstColumn = yylloc.lastColumn + 1;
	    yylloc.lastColumn += len;
	}

	// Increase location with generic new lines
	void loc_newline()
	{
	    yylloc.firstLine = yylloc.lastLine++;
	    yylloc.firstColumn = yylloc.lastColumn + 1;
	    yylloc.lastColumn = 0;
	}

	// Increase location with nested new lines
	void loc_nest_newline()
	{
	    yylloc.lastLine++;
	    yylloc.lastColumn = 0;
	}

	// Increase location with nested tokens
	void loc_nest_token(int len)
	{
	    yylloc.lastColumn += len;
	}

//	// Handle escaped characters
//	void eat_char(char c)
//	{
//	    if (string_buf_ptr+1 < string_buf + MAX_STR_LEN)
//	    {
//	        *string_buf_ptr++ = c;
//	    }
//	    else
//	    {
//	        /* handle error */
//	    }
//	}
//
//
//
//	// Handle strings
//	void eat_string()
//	{
//	    char *ptr = yytext;
//
//	    if (string_buf_ptr + yyleng < string_buf + MAX_STR_LEN)
//	    {
//	        while (*ptr != '\0')
//	            *string_buf_ptr++ = *ptr++;
//	    }
//	    else
//	    {
//	        /* handle error */
//	    }
//	}
	
	private class YYLType {
		private int firstLine;
		private int firstColumn;
		private int lastLine;
		private int lastColumn;		
	}
}
