%{
#include "parser.tab.h"
%}

%%

"{"    { return '{'; }
"}"    { return '}'; }
"="    { return EQUALS; }
";"    { return SEMICOLON; }
"+"    { return PLUS; }
"-"    { return MINUS; }
"++"   { return INC; }
"--"   { return DEC; }
[a-zA-Z][a-zA-Z0-9]*   { yylval = strdup(yytext); return ID; }
[ \t\n]    ;  /* skip whitespace */

.     { fprintf(stderr, "Nieznany symbol: %s\n", yytext); }

%%

int yywrap() {
    return 1;
}
