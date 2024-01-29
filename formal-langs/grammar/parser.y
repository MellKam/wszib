%{
#include <stdio.h>
int yylex(void);
int yyerror(char *);
%}

%error-verbose

%union {
  char* string;
  int number;
}

%token <string> VAR
%token <number> NUMBER
%token PLUS MINUS ASSIGN INCREMENT DECREMENT OPEN_BRACE CLOSE_BRACE SEMICOLON MULTIPLY DIVIDE 

%type <number> expression

%left PLUS MINUS
%left MULTIPLY DIVIDE
%%

input : /* empty */
  | input block
  ;

block : OPEN_BRACE statements CLOSE_BRACE
  | OPEN_BRACE CLOSE_BRACE
  ;

statements:
  | statements statement
  | statement
  ;

statement : VAR ASSIGN expression SEMICOLON
  | VAR ASSIGN VAR SEMICOLON
  | expression SEMICOLON
  ;

expression: NUMBER
  | VAR
  | expression PLUS expression
  | expression MINUS expression 
  | expression DIVIDE expression
  | expression MULTIPLY expression
  | expression INCREMENT
  | expression DECREMENT
  ;

%%

int main() {
  yyparse();
  return 0;
}

int yyerror(char *s) {
  printf("error: %s\n", s);
  return 0;
}