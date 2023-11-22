%{
#include <stdio.h>
%}

%option noyywrap

DIGIT [0-9]
LETTER [a-zA-Z]
WS [ \t\n]

%%

(switch|class|import|if|else|for|while|do|return|break|continue|case|default)  { 
  printf("Keyword: %s\n", yytext); 
}

(int|float|double|boolean|char) {
  printf("Basic datatype: %s\n", yytext);
}

[+-]?[0-9]+ { 
  printf("Integer literal: %s\n", yytext); 
}
[+-]?[0-9]+\.[0-9]{0,7}[fF]? { 
  printf("Float literal: %s\n", yytext); 
}
[+-]?[0-9]+\.[0-9]{0,15}[dD]? { 
  printf("Double literal: %s\n", yytext);
}
(true|false) {
  printf("Boolean literal: %s\n", yytext);
}
\"([^\\\n\"]|(\\.))*?\" {
  printf("String literal: %s\n", yytext); 
}


(\+\-\*\/) {
  printf("Arithmetic operator: %s\n", yytext);
}
"=" {
  printf("Assignment operator\n");
}

"//"([^\n])*  { 
  printf("Single-line comment: %s\n", yytext); 
}
"/*"([^*]|\n|(\*+([^*/]|\n)))*\*+"/"  { 
  printf("Multi-line comment: %s\n", yytext); 
}

[A-Za-z_]([A-Za-z0-9_])*  {
  printf("Identifier: %s\n", yytext);
}

.|\n  ;

%%
int main() {
    yylex();
    return 0;
}
