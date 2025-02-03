CC=gcc
GSL_HOME=/opt/homebrew/Cellar/gsl/2.8
CFLAGS=-I $(GSL_HOME)/include
LDFLAGS=-L $(GSL_HOME)/lib -lgsl -lgslcblas -lm
OBJ=  demo_fn.o root_finding.o

all: root_finding
root_finding:$(OBJ)
	$(CC) $(LDFLAGS) -o $@ $^

