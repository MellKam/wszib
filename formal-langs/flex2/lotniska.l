%{
#include <stdio.h>
#include <string.h>

int columnIndex;
char airportName[64];
char targetCountry[32];
%}

%option noyywrap

%%

[^;\n]*; {
    if (yytext != NULL && yytext[0] != '\0') {
        size_t length = strlen(yytext);
        if (length > 0) {
            yytext[length - 1] = '\0';
        }
    }

    if (columnIndex == 4) {
        strcpy(airportName, yytext);
    }
    if (columnIndex == 6 && strcmp(targetCountry, yytext) == 0) {
        printf("%s, %s\n", airportName, targetCountry);
    }

    if (columnIndex == 10) {
        columnIndex = 1;
    } else {
        columnIndex++;
    }
}
\n {}
.  {}

%%

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <country>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    strcpy(targetCountry, argv[1]);
    yylex();
    return 0;
}
