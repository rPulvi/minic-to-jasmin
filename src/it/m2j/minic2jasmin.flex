/* scanner.flex */

package it.m2j;

import java.io.*;
import java_cup.runtime.*;
import java.io.IOException;

/* TODO
import it.m2j.m2jSym;
import static it.m2j.m2jSym.*;
*/

%% //----------------------------------------------------------------------------------------

%class m2jLex
%public
%unicode

%line
%column

%standalone

//%cupsym it.m2j.m2jSym
//%cup

%% //----------------------------------------------------------------------------------------

"/*"   { Location.locToken(yylength()); }

/* EOF scanner.lex */
