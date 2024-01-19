build-example:
	bison -d example.y   
	flex examplelex.l
	gcc -o example example.tab.c lex.yy.c -ll

build-slowo:
	bison -d slowo.y   
	flex slowolex.l
	gcc -o slowo slowo.tab.c lex.yy.c -ll

build-stala:
	bison -d stala.y   
	flex stalalex.l
	gcc -o stala stala.tab.c lex.yy.c -ll