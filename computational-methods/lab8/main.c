#include <stdio.h>
#include <math.h>
#include <gsl/gsl_rng.h>
#include <gsl/gsl_monte.h>
#include <gsl/gsl_monte_plain.h>
#include <gsl/gsl_monte_miser.h>
#include <gsl/gsl_monte_vegas.h>

double f(double *x, size_t dim, void *params)
{
  return x[0] * x[0] + x[0] + 1; // f(x) = x^2 + x + 1
}

int main()
{
  double xl[1] = {0.0}; // Lower limit
  double xu[1] = {1.0}; // Upper limit
  size_t dim = 1;

  gsl_monte_function G = {&f, dim, NULL};

  gsl_rng *rng = gsl_rng_alloc(gsl_rng_default);
  gsl_monte_plain_state *s_plain = gsl_monte_plain_alloc(dim);
  gsl_monte_miser_state *s_miser = gsl_monte_miser_alloc(dim);
  gsl_monte_vegas_state *s_vegas = gsl_monte_vegas_alloc(dim);

  double res, err;

  FILE *output = fopen("results.csv", "w");
  if (!output)
  {
    perror("Cannot open file results.csv");
    return 1;
  }

  fprintf(output, "num_samples,plain_error,miser_error,vegas_error\n");

  for (int num_samples = 1000; num_samples <= 1000000; num_samples *= 10)
  {
    gsl_monte_plain_integrate(&G, xl, xu, dim, num_samples, rng, s_plain, &res, &err);
    double plain_error = err;

    gsl_monte_miser_integrate(&G, xl, xu, dim, num_samples, rng, s_miser, &res, &err);
    double miser_error = err;

    gsl_monte_vegas_integrate(&G, xl, xu, dim, num_samples, rng, s_vegas, &res, &err);
    double vegas_error = err;

    fprintf(output, "%d,%f,%f,%f\n", num_samples, plain_error, miser_error, vegas_error);
  }

  fclose(output);

  gsl_monte_plain_free(s_plain);
  gsl_monte_miser_free(s_miser);
  gsl_monte_vegas_free(s_vegas);
  gsl_rng_free(rng);

  return 0;
}