#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <gsl/gsl_linalg.h>
#include <gsl/gsl_blas.h>
#include <sys/resource.h>

// Funkcja do pomiaru czasu
double measure_time(struct rusage *start, struct rusage *end)
{
    double utime = (double)(end->ru_utime.tv_sec - start->ru_utime.tv_sec) +
                   1e-6 * (double)(end->ru_utime.tv_usec - start->ru_utime.tv_usec);
    return utime;
}

// Funkcja do generowania losowej macierzy nxn i wektora b o rozmiarze n
void generate_random_system(gsl_matrix *A, gsl_vector *b, int n)
{
    srand(time(NULL));
    for (int i = 0; i < n; i++)
    {
        gsl_vector_set(b, i, rand() % 10 + 1); // Losowe wartości b w zakresie [1, 10]
        for (int j = 0; j < n; j++)
        {
            gsl_matrix_set(A, i, j, (double)(rand() % 10 + 1)); // Losowe wartości A w zakresie [1, 10]
        }
    }
}

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        fprintf(stderr, "Użycie: %s <rozmiar układu równań>\n", argv[0]);
        return 1;
    }

    int n = atoi(argv[1]);
    if (n <= 0)
    {
        fprintf(stderr, "Rozmiar układu równań musi być liczbą całkowitą > 0.\n");
        return 1;
    }

    // Alokacja macierzy i wektorów
    gsl_matrix *A = gsl_matrix_alloc(n, n);
    gsl_vector *b = gsl_vector_alloc(n);
    gsl_vector *x = gsl_vector_alloc(n);

    // Generowanie losowego układu równań
    generate_random_system(A, b, n);

    // Permutacja dla dekompozycji LU
    gsl_permutation *p = gsl_permutation_alloc(n);
    int signum;

    struct rusage start, end;

    // Mierzenie czasu dekompozycji LU
    getrusage(RUSAGE_SELF, &start);
    gsl_linalg_LU_decomp(A, p, &signum);
    getrusage(RUSAGE_SELF, &end);
    double lu_decomposition_time = measure_time(&start, &end);

    // Mierzenie czasu rozwiązywania układu równań
    getrusage(RUSAGE_SELF, &start);
    gsl_linalg_LU_solve(A, p, b, x);
    getrusage(RUSAGE_SELF, &end);
    double solve_time = measure_time(&start, &end);

    // Sprawdzenie poprawności rozwiązania: Ax = b
    gsl_vector *b_check = gsl_vector_alloc(n);
    gsl_blas_dgemv(CblasNoTrans, 1.0, A, x, 0.0, b_check); // b_check = Ax
    gsl_vector_sub(b_check, b);                            // b_check = Ax - b
    double error_norm = gsl_blas_dnrm2(b_check);           // Norma ||Ax - b||

    // Wyświetlanie wyników w formacie CSV
    printf("%d,%.6f,%.6f,%e\n", 
           n, 
           lu_decomposition_time, 
           solve_time, 
           error_norm);

    // Zwolnienie pamięci
    gsl_matrix_free(A);
    gsl_vector_free(b);
    gsl_vector_free(x);
    gsl_vector_free(b_check);
    gsl_permutation_free(p);

    return 0;
}