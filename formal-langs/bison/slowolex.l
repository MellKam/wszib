/* slowolex.l - skaner wspolpracujacy z parserem slowo.y */
%{
#include "slowo.tab.h"
%}
%%
[a-zA-Z]+   { yylval = yytext;
              return T_SLOWO;
            }
.           
;      // pomin
\n          return '\n';
