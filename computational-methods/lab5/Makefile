CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: calculate_integrals_gsl

calculate_integrals_gsl: calculate_integrals_gsl.o
	$(CC) $(LDFLAGS) -o $@ $^

calculate_integrals_gsl.o: calculate_integrals_gsl.c
	$(CC) $(CFLAGS) -c calculate_integrals_gsl.c -o calculate_integrals_gsl.o

clean:
	rm -f *.o calculate_integrals_gsl