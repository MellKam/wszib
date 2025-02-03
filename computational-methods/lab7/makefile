CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: main 

main: main.o
	$(CC) $(LDFLAGS) -o $@ $^

run: main
	./main

clean:
	rm -f *.o