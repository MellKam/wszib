/* calc.y - prosty kalkulator */

%{
    #include <stdio.h>
    void yyerror(char *);
    int yylex(void);
%}

%token INTEGER 
%left '+' '-'
%left '*' '/'
%right UMINUS

%%

program:    program statement '\n'
            | program '\n'
            | /* pusta regula */
            ;

statement: expression { printf("%d\n", $1); }
  ;

expression: INTEGER
  | expression '+' expression     { $$ = $1 + $3; }
  | expression '-' expression     { $$ = $1 - $3; }
  | expression '*' expression     { $$ = $1 * $3; }
  | expression '/' expression     { $$ = $1 / $3; }
  | '-' expression   %prec UMINUS { $$ = -$2; }
  | '(' expression ')'            { $$ = $2; }
  ;

%%

void yyerror(char *s) {
    fprintf(stderr, "%s\n", s);
}

int main() {
    yyparse();
}