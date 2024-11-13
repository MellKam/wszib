#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <gsl/gsl_errno.h>
#include <gsl/gsl_spline.h>
#include <gsl/gsl_interp.h>

static double fun(double x)
{
    return 1.0 / (1.0 + 25.0 * x * x);
}

void interpolate(const char *filename, const gsl_interp_type *method, double *x, double *y, int steps, double a, double b)
{
    gsl_interp_accel *acc = gsl_interp_accel_alloc();
    gsl_spline *spline = gsl_spline_alloc(method, steps + 1);
    gsl_spline_init(spline, x, y, steps + 1);

    FILE *output = fopen(filename, "w");
    double xi;
    for (xi = a; xi <= b; xi += 0.01)
    {
        double yi = gsl_spline_eval(spline, xi, acc);
        fprintf(output, "%g %g\n", xi, yi);
    }

    fclose(output);
    gsl_spline_free(spline);
    gsl_interp_accel_free(acc);
}

int main(void)
{
    const double a = -1.0;
    const double b = 1.0;
    const int steps = 10;
    double x[100], y[100], dx;
    FILE *input;
    int i;

    input = fopen("wartosci.txt", "w");

    dx = (b - a) / (double)steps;

    for (i = 0; i <= steps; ++i)
    {
        x[i] = a + (double)i * dx;
        y[i] = fun(x[i]);
        fprintf(input, "%g %g\n", x[i], y[i]);
    }

    fclose(input);

    interpolate("inter_wielomian.txt", gsl_interp_polynomial, x, y, steps, a, b);
    interpolate("inter_liniowa.txt", gsl_interp_linear, x, y, steps, a, b);
    interpolate("inter_kubiczna.txt", gsl_interp_cspline, x, y, steps, a, b);

    return 0;
}