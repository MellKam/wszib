#include <stdio.h>
#include <math.h>
#include <gsl/gsl_chebyshev.h>

double func2(double x, void *params) {
    return 1.0 / (12.0 * x * x + 1.0);
}

void calculate_and_save(const char *filename, gsl_cheb_series *cs, double a, double b) {
    FILE *file = fopen(filename, "w");
    double step = (b - a) / 1000.0; 

    for (double x = a; x <= b; x += step) {
        double approx = gsl_cheb_eval(cs, x);
        double exact = func2(x, NULL);
        fprintf(file, "%f %f %f\n", x, exact, approx);
    }

    fclose(file);
}

int main() {
    const int n_values[] = {3, 10, 40};
    const double a = -2.0, b = 2.0; 

    gsl_function F;
    F.function = &func2;
    F.params = NULL;

    for (int i = 0; i < 3; i++) {
        int n = n_values[i];
        gsl_cheb_series *cs = gsl_cheb_alloc(n);
        gsl_cheb_init(cs, &F, a, b);

        char filename[256];
        sprintf(filename, "result_func2_n%d.dat", n);
        calculate_and_save(filename, cs, a, b);

        gsl_cheb_free(cs);
    }

    return 0;
}
