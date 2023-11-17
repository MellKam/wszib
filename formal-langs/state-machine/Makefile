PROG = log

all : ${PROG}
	./${PROG} < test_${PROG}.txt

lex.yy.c: ${PROG}.l
	flex ${PROG}.l

${PROG}: lex.yy.c
	gcc -o ${PROG} lex.yy.c -ll

clean:
	rm lex.yy.c ${PROG}