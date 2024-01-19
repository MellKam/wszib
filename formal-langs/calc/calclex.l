/* calclex.l - skaner wspolpracujacy z parserem calc.y */

%{
    #include <stdlib.h>
    #include "calc.tab.h"
%}

%%

[0-9]+      {   yylval = atoi(yytext);
                return INTEGER;
            }

[-+()=/*\n]     { return *yytext; }

[ \t]        ; /* pomin */

.            ; /* pomin */

%%

int yywrap(void) {
    return 1;
}