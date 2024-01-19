/* stalalex.l - skaner wspolpracujacy z parserem stala.y */
%{
#include "stala.tab.h"
%}
%%
[a-zA-Z]+ { 
  return T_ID;
}
[0-9]+ { 
  return T_STALA;
}
.|\n ;
