PROG = lotniska

all : ${PROG}
	./${PROG} < Lotniska.csv

lex.yy.c: ${PROG}.l
	flex ${PROG}.l

${PROG}: lex.yy.c
	gcc -o ${PROG} lex.yy.c -lfl

clean:
	rm lex.yy.c ${PROG}