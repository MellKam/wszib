#include <stdio.h>
#include <gsl/gsl_errno.h>
#include <gsl/gsl_math.h>
#include <gsl/gsl_roots.h>
#include <math.h>
#include "demo_fn.h"

void secant_method(double (*f)(double, void *), struct quadratic_params *params, double x0, double x1, double tol)
{
    double x2, f0, f1, f2;
    int iter = 0, max_iter = 100;

    f0 = f(x0, params);
    f1 = f(x1, params);

    printf("Secant method:\n");

    while (iter < max_iter)
    {
        if (f1 - f0 == 0)
        {
            printf("Error: Division by zero in secant method.\n");
            return;
        }
        x2 = x1 - f1 * (x1 - x0) / (f1 - f0);
        f2 = f(x2, params);

        printf("Iter %d: x = %.7f, f(x) = %.7f\n", iter, x2, f2);

        if (fabs(f2) < tol)
        {
            printf("Converged to %.7f after %d iterations.\n", x2, iter);
            return;
        }

        x0 = x1;
        x1 = x2;
        f0 = f1;
        f1 = f2;

        iter++;
    }

    printf("Did not converge after %d iterations.\n", iter);
}

void find_root(const gsl_root_fsolver_type *T, const char *method_name,
               double x_lo, double x_hi, struct quadratic_params params)
{
    gsl_root_fsolver *s;
    gsl_function F;
    int status, iter = 0, max_iter = 100;
    double r = 0;

    F.function = &quadratic;
    F.params = &params;

    double f_lo = quadratic(x_lo, &params);
    double f_hi = quadratic(x_hi, &params);
    if (f_lo * f_hi > 0)
    {
        printf("\nError: The interval [%f, %f] does not straddle y=0.\n", x_lo, x_hi);
        return;
    }

    s = gsl_root_fsolver_alloc(T);
    gsl_root_fsolver_set(s, &F, x_lo, x_hi);

    printf("\nUsing %s method:\n", method_name);
    printf("%5s [%9s, %9s] %9s %10s\n", "Iter", "Lower", "Upper", "Root", "Err(Est)");

    do
    {
        iter++;
        status = gsl_root_fsolver_iterate(s);
        r = gsl_root_fsolver_root(s);
        x_lo = gsl_root_fsolver_x_lower(s);
        x_hi = gsl_root_fsolver_x_upper(s);
        status = gsl_root_test_interval(x_lo, x_hi, 0, 0.001);

        printf("%5d [%.7f, %.7f] %.7f %.7f\n", iter, x_lo, x_hi, r, x_hi - x_lo);
    } while (status == GSL_CONTINUE && iter < max_iter);

    if (status == GSL_SUCCESS)
    {
        printf("Converged to %.7f after %d iterations.\n", r, iter);
    }
    else
    {
        printf("Did not converge after %d iterations.\n", iter);
    }

    gsl_root_fsolver_free(s);
}

int main(void)
{
    struct quadratic_params params1 = {1.0, 0.0, -5.0}; // Równanie: x^2 - 5 = 0
    struct quadratic_params params2 = {1.0, -2.0, 1.0}; // Równanie: x^2 - 2x + 1 = 0

    printf("Finding root for equation x^2 - 5 = 0\n");
    find_root(gsl_root_fsolver_bisection, "Bisection", 0.0, 5.0, params1);
    find_root(gsl_root_fsolver_brent, "Brent-Dekker", 0.0, 5.0, params1);

    secant_method(quadratic, &params1, 0.0, 5.0, 0.001);

    printf("\nFinding root for equation x^2 - 2x + 1 = 0\n");
    find_root(gsl_root_fsolver_bisection, "Bisection", 0.0, 1.5, params2);
    find_root(gsl_root_fsolver_brent, "Brent-Dekker", 0.0, 1.5, params2);

    secant_method(quadratic, &params2, 0.0, 1.5, 0.001);

    return 0;
}