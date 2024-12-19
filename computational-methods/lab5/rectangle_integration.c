#include <stdio.h>
#include <math.h>

#define PI 3.14159265358979323846

double f1(double x)
{
    return x * x + x + 1;
}

double f2(double x)
{
    return sqrt(1 - x * x);
}

double f3(double x)
{
    if (x <= 0)
        return NAN;
    return 1 / sqrt(x);
}

// Funkcja obliczająca całkę metodą prostokątów
double rectangle_integral(double (*f)(double), double a, double b, int n)
{
    double h = (b - a) / n;
    double sum = 0;
    for (int i = 0; i < n; i++)
    {
        sum += f(a + i * h);
    }
    return sum * h;
}

int main()
{
    double a = 0, b = 1;

    // Tolerancje błędów
    double tolerances[] = {1e-3, 1e-4, 1e-5, 1e-6};

    // Funkcja f1(x) = x^2 + x + 1
    printf("Funkcja f1(x) = x^2 + x + 1:\n");
    double exact_value_f1 = 5.0 / 3.0; // Dokładna wartość
    printf("Dokładna wartość całki: %.10f\n", exact_value_f1);
    for (int i = 0; i < 4; i++)
    {
        double tolerance = tolerances[i];
        int n = 4;
        double result, error;
        do
        {
            result = rectangle_integral(f1, a, b, n);
            error = fabs(exact_value_f1 - result);
            if (error <= tolerance)
                break;
            n *= 2;
        } while (n <= 1000000);
        printf("Podprzedziały: %d | Przybliżona całka: %.10f | Błąd: %.10f (dla dokładności %.1e)\n", n, result, error, tolerance);
    }

    // Funkcja f2(x) = sqrt(1 - x^2)
    printf("\nFunkcja f2(x) = sqrt(1 - x^2):\n");
    double exact_value_f2 = PI / 4.0;
    printf("Dokładna wartość całki: %.10f\n", exact_value_f2);
    for (int i = 0; i < 4; i++)
    {
        double tolerance = tolerances[i];
        int n = 4;
        double result, error;
        do
        {
            result = rectangle_integral(f2, a, b, n);
            error = fabs(exact_value_f2 - result);
            if (error <= tolerance)
                break;
            n *= 2;
        } while (n <= 1000000);
        printf("Podprzedziały: %d | Przybliżona całka: %.10f | Błąd: %.10f (dla dokładności %.1e)\n", n, result, error, tolerance);
    }

    // Funkcja f3(x) = 1/sqrt(x)
    printf("\nFunkcja f3(x) = 1/sqrt(x):\n");
    double exact_value_f3 = 2.0;
    printf("Dokładna wartość całki: %.10f\n", exact_value_f3);
    for (int i = 0; i < 4; i++)
    {
        double tolerance = tolerances[i];
        int n = 4;
        double result, error;
        do
        {
            result = rectangle_integral(f3, a + 0.0001, b, n);
            error = fabs(exact_value_f3 - result);
            if (error <= tolerance)
                break;
            n *= 2;
        } while (n <= 1000000);
        printf("Podprzedziały: %d | Przybliżona całka: %.10f | Błąd: %.10f (dla dokładności %.1e)\n", n, result, error, tolerance);
    }

    return 0;
}