/* stala.y - przyklad ilustrujacy przekazanie do prasera nazwy identyfikatora
             i wartosci stalej */
%{
#include <stdio.h>
int yylex(void);
int yyerror(char *);
%} 

%token T_ID
%token T_STALA
%%
input : input element
      | // pusta produkcja
      ;
element : T_ID {  
          printf("Identyfikator: %s\n", $1); 
        }
        | T_STALA {
          printf("Stala: %s\n", $1);
        }
        ;
%%
int main(){
  yyparse();
  return
}
int yyerror(char *s) {
   printf("blad: %s\n", s);
   return 0;
}