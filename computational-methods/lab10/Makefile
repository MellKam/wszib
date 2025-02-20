CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8
CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm

all: brent_minim quad_golden

brent_minim: brent_minim.o
	$(CC) $(LDFLAGS) -o $@ $^

quad_golden: quad_golden.o
	$(CC) $(LDFLAGS) -o $@ $^

run: brent_minim
	./brent_minim

run2: quad_golden
	./quad_golden

clean:
	rm -f *.o

plot:
	gnuplot diagram/plot.gp