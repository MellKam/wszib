CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: task2

task2: task2.o
	$(CC) $(LDFLAGS) -o $@ $^

task2.o: task2.c
	$(CC) $(CFLAGS) -c task2.c -o task2.o

clean:
	rm -f *.o task2