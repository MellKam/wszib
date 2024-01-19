%{
#include <stdlib.h>
#include "calc_3.tab.h"
%}

%%
([0-9]+)|([0-9]+\.[0-9]+)|([0-9]+\.[0-9]*(e|E)[0-9]+)  {  
  yylval.fval = atof(yytext);
  return FLOAT;
}

[-+/*()\^=\n]     { return *yytext; }
[\t ]                         ;
.                             ;

%%

int yywrap(void) {
    return 1;
}