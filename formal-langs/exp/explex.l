/* explex.l - skaner wspolpracujacy z parserem exp.y */
%{
#include "exp.tab.h"
%}

%%

[0-9]+  { 
  yylval = atoi(yytext); 
  return NUM; 
}
[ \t]   ;
.|\n    return *yytext;
