# Sprawozdanie

## Laboratorium nr 2: Metody obliczeniowe

**Autor:** MelKam  
**Data:** [Wstaw datę]

### 1. Co było do zrobienia

- Skompilować program `dokladnosc.c` przy użyciu polecenia `make`, a następnie uruchomić go.
- Korzystając z funkcji `gsl_ieee_printf_double`, zobaczyć, jak zmienia się mantysa i cecha dla coraz mniejszych liczb. Określić, kiedy mantysa nie jest w postaci znormalizowanej.
- Odtworzyć wykres znajdujący się na rysunku przy pomocy `gnuplot`.
- Narysować dane zgromadzone w pliku `dane1.dat` z jedną z osi w skali logarytmicznej.
- Narysować wykres funkcji dwuwymiarowej z punktami w pliku `dane2.dat`, znaleźć maksimum i zaznaczyć je na wykresie jako notkę.

### 2. Moje podejście do rozwiązania problemu

- Użyłem `make` do kompilacji programu `dokladnosc.c` i uruchomiłem go, aby obserwować zmiany w mantysie i cesze dla coraz mniejszych liczb.
- W `gnuplot` stworzyłem trzy różne wykresy:
  - Wykres z pliku `chart.gpl` odtwarzający dany rysunek.
  - Wykres z pliku `chart2.gpl` z danymi z `dane1.dat` z osią w skali logarytmicznej.
  - Wykres z pliku `chart3.gpl` z danymi z `dane2.dat`, z zaznaczeniem maksimum.

### 3. Wyniki

- **Program `dokladnosc.c`:**
  - Wyniki pokazują zmiany w mantysie i cesze dla coraz mniejszych liczb.
  - Mantysa przestaje być znormalizowana przy bardzo małych liczbach.
- **Wykresy `gnuplot`:**
  - Wykres z `chart.gpl` odtwarza dany rysunek.
  - Wykres z `chart2.gpl` przedstawia dane z `dane1.dat` z osią czasu w skali logarytmicznej.
  - Wykres z `chart3.gpl` przedstawia dane z `dane2.dat` z zaznaczonym maksimum.

### 4. Wnioski

- Program `dokladnosc.c` pokazuje, jak zmieniają się reprezentacje liczb zmiennoprzecinkowych w zależności od ich wielkości.
- Mantysa przestaje być znormalizowana, gdy liczba jest zbyt mała, aby jej najważniejszy bit był równy 1.
- Wykresy `gnuplot` umożliwiają wizualizację danych i funkcji, co ułatwia analizę i interpretację wyników.
- Skala logarytmiczna jest przydatna do przedstawiania danych rozciągających się na wiele rzędów wielkości.
- Zaznaczenie maksimum na wykresie pomaga w identyfikacji i analizie kluczowych punktów danych.

```c
#include <stdio.h>
#include <gsl/gsl_ieee_utils.h>

int main (void)
{
  float f = 1.0/3.0;
  double d = 1.0/3.0;

  double fd = f; /* promote from float to double */

  printf(" f="); gsl_ieee_printf_float(&f);
  printf("\n");

  printf("fd="); gsl_ieee_printf_double(&fd);
  printf("\n");

  printf(" d="); gsl_ieee_printf_double(&d);
  printf("\n");

  double num = 1.0;
  for (int i = 0; i < 10; i++) {
    printf("num = "); gsl_ieee_printf_double(&num);
    printf("\n");
    num /= 2.0;
  }

  return 0;
}
```
