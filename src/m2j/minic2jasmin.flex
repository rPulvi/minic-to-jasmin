/* scanner.flex */

package m2j;

import java_cup.runtime.*;
import java.io.IOException;


import m2j.minic2jasminSym;
import static m2j.minic2jasminSym.*;


%% //----------------------------------------------------------------------------------------

%class m2jLex
%public
%unicode

%line
%column

%standalone

%cupsym m2j.minic2jasminSym
%cup

%xstate comment
%xstate singleline_comment
%xstate quote

Integer = ([1-9][0-9]*|0)
Float = ([1-9]{1,1}[0-9]*"."[0-9]+)|(0"."[0-9]+)
Identifier = [a-zA-Z_]+[a-zA-Z0-9_]*
EOL = \r|\n|\r\n


%{
	minic2jasminSym symClass;
	String retString = "";
	
	private Symbol sym(int type)
	{
		return sym(type, yytext());
	}

	private Symbol sym(int type, Object value)
	{
		return new Symbol(type, yyline, yycolumn, value); 
	}

	private void error(String errMsg) throws IOException
	{
		throw new IOException("Error at (" + ++yyline + "," + ++yycolumn +"). " + errMsg + " " + yytext() );
	}
%}

%% //----------------------------------------------------------------------------------------

"/*"   { yybegin(comment); }

<comment>"/*"       { yybegin(comment); }
<comment>"*/"       { yybegin(YYINITIAL); }
<comment>"\n"       { ; }
<comment><<EOF>>    { error("Manca il token di fine commento '*/'"); } 
<comment>.          { ; }

"*/"             	{ error("Fine inattesa del commento"); }
                    
"//"				{ yybegin(singleline_comment);}
<singleline_comment>.       { ; }
<singleline_comment>"\n"    { yybegin(YYINITIAL); }

"\""                { yybegin(quote); }

<quote>"\""         { 	yybegin(YYINITIAL); 
						return sym(CONST_STRING, retString); 	
					}
  
<quote>.+"\\n"	    { retString = yytext(); }

<quote>"\\".        { ; }

<quote><<EOF>>      { error("Fine inattesa della stringa"); }

<quote>[^\\\n\"]+   { retString = yytext(); }


"bool"              { return sym(TYPE_BOOL); }
"int"               { return sym(TYPE_INT); }
"float"             { return sym(TYPE_FLOAT); }
"string"            { return sym(TYPE_STRING); }
"void"              { return sym(TYPE_VOID); }
"extern"            { return sym(EXTERN); }

"if"                { return sym(IF); }
"else"              { return sym(ELSE); }
"while"             { return sym(WHILE); }

"new"               { return sym(NEW); }
"return"            { return sym(RETURN); }
"="                 { return sym(ASSIGN); }

"+"                 { return sym(OP_PLUS); }
"-"                 { return sym(OP_DIFF); }
"*"                 { return sym(OP_MUL); }
"/"                 { return sym(OP_DIV); }
"%"                 { return sym(OP_MOD); }

"=="                { return sym(OP_EQ); }
"!="                { return sym(OP_NEQ); }
"<"                 { return sym(OP_LT); }
"<="                { return sym(OP_LET); }
">"                 { return sym(OP_GT); }
">="                { return sym(OP_GET); }

"&&"               	{ return sym(OP_AND); }
"||"                { return sym(OP_OR); }
"!"               	{ return sym(OP_NOT); }

","                 { return sym(COMMA); }
";"                 { return sym(SEMI); }

"("                 { return sym(BRA_O); }
")"                 { return sym(BRA_C); }
"["                 { return sym(BRA_OS); }
"]"                 { return sym(BRA_CS); }

"{"		    		{ return sym(BRA_OG); }
"}"		    		{ return sym(BRA_CG); }

"true"              { return sym(CONST_BOOL, new Boolean(yytext())); }
"false"             { return sym(CONST_BOOL, new Boolean(yytext())); }

{Integer}           { return sym(CONST_INT, new Integer(yytext())); }
{Float}             { return sym(CONST_FLOAT, new Float(yytext())); }
{Identifier}        { return sym(ID); }


EOL					{ ; }
"\n"      			{ retString = yytext();}
"\t"				{ if (yycolumn==0) yycolumn+=4; 
					  else yycolumn+=3;}
" "					{ ; }

	
	
[0-9]+{Identifier}	{ error("identificatore non valido"); }

.                   { error("Simbolo sconosciuto"); } // ERROR: unknown token
