%{
#include <stdio.h>
#include "./parser.tab.h"
%}

%option noyywrap
%%

"+" { return PLUS; }
"-" { return MINUS; }
"/" { return DIVIDE; }
"*" { return MULTIPLY; }
"++" { return INCREMENT; }
"--" { return DECREMENT; }
"{" { return OPEN_BRACE; }
"}" { return CLOSE_BRACE; }
";" { return SEMICOLON; }
"=" { return ASSIGN; }

[A-Za-z_][A-Za-z0-9_]* {
  yylval.string = yytext;
  return VAR;
}
[0-9]+ {
  yylval.number = atoi(yytext);
  return NUMBER;
}

\n ;
.  ;
%%
