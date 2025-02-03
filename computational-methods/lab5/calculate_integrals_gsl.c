#include <stdio.h>
#include <gsl/gsl_integration.h>
#include <gsl/gsl_errno.h>
#include <math.h>

double f3(double x, void *params)
{
    return sin(x);
}

double f4(double x, void *params)
{
    return tan(x);
}

double f5(double x, void *params)
{
    return log(x + x * x);
}

void gsl_custom_error_handler(const char *reason, const char *file, int line, int gsl_errno)
{
    fprintf(stderr, "GSL error: %s:%d: %s (GSL errno: %d)\n", file, line, reason, gsl_errno);
}

void calculate_integral_gsl(double (*f)(double, void *), double a, double b, double epsrel, const char *name)
{
    gsl_integration_workspace *workspace = gsl_integration_workspace_alloc(1000);
    gsl_function F;
    F.function = f;
    double result, error;
    int status = gsl_integration_qags(&F, a, b, 0, epsrel, 1000, workspace, &result, &error);

    if (status == GSL_SUCCESS) {
        printf("Function: %s, epsrel: %.1e, result: %.10f, error: %.10f, number of intervals: %zu\n",
               name, epsrel, result, error, workspace->size);
    } else {
        fprintf(stderr, "Failed to compute the integral for function %s with epsrel: %.1e\n", name, epsrel);
    }

    gsl_integration_workspace_free(workspace);
}

double rectangle_method(double (*f)(double, void *), double a, double b, double epsrel)
{
    int n = 1;
    double h = (b - a) / n;
    double result = 0.0, prev_result;

    do
    {
        prev_result = result;
        result = 0.0;
        for (int i = 0; i < n; i++)
        {
            double x = a + (i + 0.5) * h;
            result += f(x, NULL);
        }
        result *= h;
        n *= 2;
        h = (b - a) / n;
    } while (fabs(result - prev_result) > epsrel);

    return result;
}

int main()
{
    gsl_set_error_handler(&gsl_custom_error_handler);

    double epsrels[] = {1e-3, 1e-4, 1e-5, 1e-6};

    for (int i = 0; i < 4; i++)
    {
        printf("\nAccuracy epsrel: %.1e\n", epsrels[i]);

        calculate_integral_gsl(f3, 0.0, M_PI, epsrels[i], "f3: sin(x)");

        calculate_integral_gsl(f4, 1e-6, M_PI / 2 - 1e-6, epsrels[i], "f4: tan(x)");

        calculate_integral_gsl(f5, 1.0, 4.0, epsrels[i], "f5: log(x + x^2)");

        printf("Rectangle method for function f3: sin(x), epsrel %.1e: %.10f\n", epsrels[i], rectangle_method(f3, 0.0, M_PI, epsrels[i]));
        printf("Rectangle method for function f4: tan(x), epsrel %.1e: %.10f\n", epsrels[i], rectangle_method(f4, 1e-6, M_PI / 2 - 1e-6, epsrels[i]));
        printf("Rectangle method for function f5: log(x + x^2), epsrel %.1e: %.10f\n", epsrels[i], rectangle_method(f5, 1.0, 4.0, epsrels[i]));
    }

    return 0;
}