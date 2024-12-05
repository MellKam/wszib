#include <stdio.h>
#include <gsl/gsl_ieee_utils.h>

int main (void) 
{
  float f = 1.0/3.0;
  double d = 1.0/3.0;

  double fd = f; /* promote from float to double */
  
  printf(" f="); gsl_ieee_printf_float(&f); 
  printf("\n");

  printf("fd="); gsl_ieee_printf_double(&fd); 
  printf("\n");

  printf(" d="); gsl_ieee_printf_double(&d); 
  printf("\n");

  double num = 1.0;
  for (int i = 0; i < 10; i++) {
    printf("num = "); gsl_ieee_printf_double(&num);
    printf("\n");
    num /= 2.0;
  }

  return 0;
}
