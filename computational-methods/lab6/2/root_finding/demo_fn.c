#include "demo_fn.h"

double quadratic(double x, void *params)
{
  struct quadratic_params *p = (struct quadratic_params *)params;
  return p->a * x * x + p->b * x + p->c;
}