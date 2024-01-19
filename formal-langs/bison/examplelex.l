/* examplelex.l - skaner wspolpracujacy z parserem example.y */
%{
#include "example.tab.h"
%}
%%
[a-z]   { yylval = yytext[0];
          return T_CHAR;
        }
.|\n ;
