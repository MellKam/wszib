%{
#include <stdio.h>
int yylex(void);
int yyerror(char *);
%}
%token T_CHAR
%%
input :  input T_CHAR { printf("rozpoznano - %c\n", $2); }
      |  /* pusta produkcja */
      ;
%%

int main() {
    yyparse();
    return 0;
}

int yyerror(char *s) {
    printf("blad: %s\n", s);
    return 0;
}
