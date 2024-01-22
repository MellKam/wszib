%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
%}

%option noyywrap

%{
// Function to remove diacritical marks from a string
void removeDiacriticalMarks(char *str) {
    // Implementation of diacritical marks removal (you may need to add more cases)
    // ...
}

// Function to print airports for a specified country
void printAirportsForCountry(char *country) {
    // Implementation to read the CSV file and print airports for the specified country
    // ...
}
%}

DIGIT [0-9]
ALPHA [a-zA-Z]
ALPHANUM [a-zA-Z0-9]

%%
\n      /* ignore newlines */
" "     /* ignore spaces */
";"     /* semicolon is a separator */

{ALPHA}+    { removeDiacriticalMarks(yytext); }
{ALPHA}+"," { printAirportsForCountry(yytext); }

.       /* ignore any other characters */

%%

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <country>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    yyin = fopen("Lotniska.csv", "r");
    if (yyin == NULL) {
        perror("Error opening file");
        exit(EXIT_FAILURE);
    }

    printAirportsForCountry(argv[1]);

    fclose(yyin);
    return 0;
}
