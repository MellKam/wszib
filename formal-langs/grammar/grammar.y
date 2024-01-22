%{
#include <stdio.h>
%}

%token ID EQUALS SEMICOLON PLUS MINUS INC DEC

%%

block: '{' statements '}'
     {
         printf("Poprawny blok instrukcji\n");
     }
     ;

statements: /* empty */
          | statements statement
          ;

statement: assignment_statement
         | increment_statement
         | decrement_statement
         ;

assignment_statement: ID EQUALS ID SEMICOLON
                  ;

increment_statement: ID INC SEMICOLON
                  ;

decrement_statement: ID DEC SEMICOLON
                  ;

%%

int main() {
    yyparse();
    return 0;
}
