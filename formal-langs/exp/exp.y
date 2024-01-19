%{
#include <stdio.h>
int yylex(void);
void yyerror(char *);
%}

%token NUM
%left '-'
%%
input: /* empty */
   | input line
   ;

line: '\n'
   | exp '\n'    { printf("%d\n", $1); }
   ;

exp: NUM
   | exp '-' exp       { $$ = $1 - $3; }
   | '(' exp ')'       { $$ = $2; }
   ;
%%

void yyerror(char *s) {
   fprintf(stderr, "blad: %s\n", s);
}

int main() {
   yyparse();
}
