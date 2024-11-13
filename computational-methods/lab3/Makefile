CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: interpolacja

interpolacja: interpolacja.o
	$(CC) $(LDFLAGS) -o $@ $^

interpolacja.o: interpolacja.c
	$(CC) $(CFLAGS) -c interpolacja.c -o interpolacja.o

clean:
	rm -f *.o interpolacja