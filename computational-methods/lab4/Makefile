CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: func3

func3: func3.o
	$(CC) $(LDFLAGS) -o $@ $^

func3.o: func3.c
	$(CC) $(CFLAGS) -c func3.c -o func3.o

clean:
	rm -f *.o func3