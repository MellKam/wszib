PROG = parser
OS := $(shell uname)

ifeq ($(OS), Linux)
	LIBS = -lfl
else
	LIBS = -ll
endif

${PROG}: ${PROG}.tab.c lex.yy.c
	gcc -o ${PROG} ${PROG}.tab.c lex.yy.c ${LIBS}

${PROG}.tab.c: ${PROG}.y
	bison -d ${PROG}.y

lex.yy.c: lexer.l
	flex lexer.l

run: ${PROG}
	./${PROG} < test_blok_instrukcji.txt

clean:
	rm -f ${PROG} lex.yy.c ${PROG}.tab.c ${PROG}.tab.h