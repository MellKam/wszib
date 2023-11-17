%s COLUMN_2 COLUMN_3

%%

#.*   ;

^[A-Za-z0-9_-]+  {
  printf("nazwa serwisu: %s\n", yytext);
  BEGIN(COLUMN_2);
}
<COLUMN_2>[0-9]+	{
  printf("numer portu %s\n", yytext);
}
<COLUMN_2>'/'  ;
<COLUMN_2>(udp|tcp) {
  printf("nazwa protokolu %s\n", yytext);
  BEGIN(COLUMN_3);
}

<COLUMN_3>[A-Za-z0-9_-]+ {
  printf("nazwa aliasu: %s\n", yytext);
}
<COLUMN_3>#.*$ {
  printf("opis: %s\n", yytext + 3);
}
<COLUMN_3>$ {
  BEGIN(INITIAL);
}
.|\n		 ;

%%

int main() {
  yylex();
}