#include <stdio.h>
#include <math.h>
#include <float.h>
#include <stdbool.h>

#define PI 3.14159265358979323846
#define MAX_SUBDIVISIONS 1000000
#define NUM_TOLERANCES 4

double rectangle_integral(double (*func)(double), double start, double end, int subdivisions) {
    double step = (end - start) / subdivisions;
    double sum = 0.0;
    
    for (int i = 0; i < subdivisions; i++) {
        sum += func(start + i * step);
    }
    
    return sum * step;
}

double compute_adaptive_integral(
    double (*func)(double), 
    double start, 
    double end, 
    double exact_value, 
    double tolerance
) {
    int subdivisions = 4;
    double result, error;
    
    while (true) {
        result = rectangle_integral(func, start, end, subdivisions);
        error = fabs(exact_value - result);
        
        if (error <= tolerance || subdivisions >= MAX_SUBDIVISIONS) {
            break;
        }
        
        subdivisions *= 2;
    };
    
    return result;
}

double quadratic_function(double x) {
    return x * x + x + 1;
}

double semicircle_function(double x) {
    return sqrt(1 - x * x);
}

double inverse_sqrt_function(double x) {
    return (x <= 0) ? NAN : 1 / sqrt(x);
}

void print_integration_results(
    const char* function_name, 
    double (*func)(double), 
    double start, 
    double end, 
    double exact_value, 
    double tolerances[]
) {
    printf("\n%s:\n", function_name);
    printf("Exact integral value: %.10f\n", exact_value);
    
    for (int i = 0; i < NUM_TOLERANCES; i++) {
        double tolerance = tolerances[i];
        
        double adjusted_start = (func == inverse_sqrt_function) ? start + 0.0001 : start;
        
        double result = compute_adaptive_integral(func, adjusted_start, end, exact_value, tolerance);
        double error = fabs(exact_value - result);
        
        printf("Subdivisions: %d | Approximated integral: %.10f | Error: %.10f (tolerance %.1e)\n", 
               (int)pow(2, i + 2), result, error, tolerance);
    }
}

int main() {
    double start = 0.0, end = 1.0;
    double tolerances[] = {1e-3, 1e-4, 1e-5, 1e-6};
    
    print_integration_results(
        "f1(x) = x^2 + x + 1", 
        quadratic_function, 
        start, end, 
        5.0 / 3.0, 
        tolerances
    );
    
    print_integration_results(
        "f2(x) = sqrt(1 - x^2)", 
        semicircle_function, 
        start, end, 
        PI / 4.0, 
        tolerances
    );
    
    print_integration_results(
        "f3(x) = 1/sqrt(x)", 
        inverse_sqrt_function, 
        start, end, 
        2.0, 
        tolerances
    );
    
    return 0;
}