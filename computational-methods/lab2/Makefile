CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8

CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: dokladnosc

dokladnosc: dokladnosc.o
	$(CC) $(LDFLAGS) -o $@ $^

dokladnosc.o: dokladnosc.c
	$(CC) $(CFLAGS) -c dokladnosc.c -o dokladnosc.o

clean:
	rm -f *.o dokladnosc
