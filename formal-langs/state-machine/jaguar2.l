%{
  /* jaguar2.l
   Rozpoznawanie znaczenia slowa jaguar w zaleznosci od lewego kontekstu 
   przy uzyciu stanow */
%}

%s A B

%%
^a		 BEGIN(A);
^b 		 BEGIN(B);
<A>jaguar	 printf("Duzy kot\n");
<B>jaguar	 printf("Sportowy samochod\n");
.|\n		 ;