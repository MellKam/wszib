CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: interpolacja1

interpolacja1: interpolacja1.o
	$(CC) $(LDFLAGS) -o $@ $^

interpolacja1.o: interpolacja1.c
	$(CC) $(CFLAGS) -c interpolacja1.c -o interpolacja1.o

clean:
	rm -f *.o interpolacja1