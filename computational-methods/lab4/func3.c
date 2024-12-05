#include <stdio.h>
#include <math.h>
#include <gsl/gsl_chebyshev.h>
#include <sys/stat.h>

double f3(double x)
{
    double A = 0.05;
    double B = 50.0;
    return cos(x) + A * sin(B * x);
}

double func_wrapper(double x, void *params)
{
    double (*f)(double) = params;
    return f(x);
}

void aproksymacja(double (*f)(double), double a, double b, int n, const char *filename)
{
    gsl_cheb_series *cheb = gsl_cheb_alloc(n);

    gsl_function F;
    F.function = &func_wrapper;
    F.params = f;

    gsl_cheb_init(cheb, &F, a, b);

    FILE *fp = fopen(filename, "w");
    if (!fp)
    {
        fprintf(stderr, "Error opening file: %s\n", filename);
        gsl_cheb_free(cheb);
        return;
    }

    double x, y, y_cheb;
    for (x = a; x <= b; x += 0.01)
    {
        y = f(x);
        y_cheb = gsl_cheb_eval(cheb, x);
        fprintf(fp, "%g %g %g\n", x, y, y_cheb);
    }

    fclose(fp);
    gsl_cheb_free(cheb);
}

int main()
{
    aproksymacja(f3, 0, 10, 3, "result_func3_n3.dat");
    aproksymacja(f3, 0, 10, 10, "result_func3_n10.dat");
    aproksymacja(f3, 0, 10, 40, "result_func3_n40.dat");

    return 0;
}