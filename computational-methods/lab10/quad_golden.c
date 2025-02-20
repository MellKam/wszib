#include <stdio.h>
#include <math.h>
#include <time.h>

// Definicja funkcji do minimalizacji
double fn1(double x, void *params)
{
    return exp(-x) - sin(x) + sqrt(x);
}

// Funkcja do obliczania interpolacji kwadratowej
double quadratic_interpolation(double a, double b, double c, double fa, double fb, double fc)
{
    // Wyznaczanie współczynników paraboli
    double denominator = (fa - fb) * (fa - fc) * (fb - fc);
    double numerator = (a * (fa - fb) * (fa - fc) + b * (fb - fa) * (fb - fc) + c * (fc - fa) * (fc - fb));

    return numerator / denominator;
}

void golden_section_method(double a, double b, double epsilon)
{
    int iter = 0;
    double alpha = (1.0 + sqrt(5.0)) / 2.0; // Złoty stosunek
    double x1, x2, f1, f2;

    printf("Starting Golden Section Method\n");
    printf("Initial Interval: [%.7f, %.7f]\n", a, b);

    // Pętla iteracyjna
    while ((b - a) > epsilon)
    {
        // Obliczanie punktów x1 i x2 za pomocą złotego podziału
        x1 = a + (b - a) / alpha;
        x2 = b - (b - a) / alpha;

        // Obliczanie wartości funkcji w punktach
        f1 = fn1(x1, NULL);
        f2 = fn1(x2, NULL);

        // Wybór nowego przedziału na podstawie wartości funkcji
        if (f1 < f2)
            b = x2;
        else
            a = x1;

        iter++;
    }

    double minimum = (a + b) / 2.0;
    printf("Golden Section Method completed in %d iterations\n", iter);
    printf("Found minimum at x = %.7f\n", minimum);
}

void brent_method(double a, double b, double epsilon)
{
    int iter = 0, max_iter = 100;
    double x1, x2, f1, f2, x_quad, f_quad;
    printf("Starting Brent's Method with Golden Section and Quadratic Interpolation\n");
    printf("Initial Interval: [%.7f, %.7f]\n", a, b);

    // Pętla iteracyjna
    while ((b - a) > epsilon && iter < max_iter)
    {
        // Obliczanie punktów x1 i x2 za pomocą złotego podziału
        x1 = a + (b - a) / ((1.0 + sqrt(5.0)) / 2.0);
        x2 = b - (b - a) / ((1.0 + sqrt(5.0)) / 2.0);

        // Obliczanie wartości funkcji w punktach
        f1 = fn1(x1, NULL);
        f2 = fn1(x2, NULL);

        // Jeśli postęp jest wystarczająco mały, zastosuj interpolację kwadratową
        if ((b - a) < epsilon)
        {
            x_quad = quadratic_interpolation(a, x1, x2, fn1(a, NULL), f1, f2);
            f_quad = fn1(x_quad, NULL);
            printf("Using quadratic interpolation at x = %.7f f(x) = %.7f\n", x_quad, f_quad);
            break;
        }

        // Wybór nowego przedziału na podstawie wartości funkcji
        if (f1 < f2)
            b = x2;
        else
            a = x1;

        // Wyświetlanie wyników iteracji
        iter++;
    }

    double minimum = (a + b) / 2.0;
    printf("Brent's Method completed in %d iterations\n", iter);
    printf("Found minimum at x = %.7f\n", minimum);
}

// Funkcja do generowania punktów wykresu
void generate_plot_data(double a, double b, int num_points)
{
    FILE *fp = fopen("plot_data.txt", "w");
    double step = (b - a) / (num_points - 1);
    for (int i = 0; i < num_points; i++)
    {
        double x = a + i * step;
        double y = fn1(x, NULL);
        fprintf(fp, "%.7f %.7f\n", x, y);
    }
    fclose(fp);
}

int main(void)
{
    double a = M_PI / 2.0, b = 4.0 * M_PI; // Nowy przedział [π/2, 4π]
    double epsilon = 1e-6;                 // Tolerancja
    int num_points = 100;                  // Liczba punktów do wykresu

    // Generowanie danych do wykresu
    generate_plot_data(a, b, num_points);

    // Mierzenie czasu dla Złotego Podziału
    clock_t start = clock();
    golden_section_method(a, b, epsilon);
    clock_t end = clock();
    double time_taken = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("Time taken for Golden Section Method: %.7f seconds\n", time_taken);

    // Mierzenie czasu dla Brent's Method
    start = clock();
    brent_method(a, b, epsilon);
    end = clock();
    time_taken = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("Time taken for Brent's Method: %.7f seconds\n", time_taken);

    return 0;
}