%{
#include <stdio.h>
#include <stdlib.h>
int yylex(void);
int yyerror(char *);
%}

%union {
  char *str;
}

%define api.value.type {union YYSTYPE}

%token <str> T_SLOWO

%%
input : input line '\n'
      | input '\n'
      | // pusta produkcja
        ;
line : line T_SLOWO { printf("znaleziono slowo: %s\n", $2); free($2); }
     | T_SLOWO { printf("znaleziono slowo: %s\n", $1); free($1); }
        ;
%%

int main(){
    yyparse();
    return 0;
}

int yyerror(char *s) {
    printf("blad: %s\n", s);
    return 0;
}
