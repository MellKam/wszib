%{
/* jaguar1.l
Rozpoznawanie znaczenia slowa jaguar
w zaleznosci od kontekstu z wykorzystaniem flagi */
char flag;
%}
%%
^a flag = 'a';
^b flag = 'b';

jaguar switch(flag) {
  case 'a': 
    printf("Duzy kot\n"); 
    break;
  case 'b': 
    printf("Samochod sportowy\n"); 
    break;
}
.|\n ;

