Napisz program, który czyta z pliku wejściowego input.dat listę słów oddzielonych białymi znakami (można wykorzystać funkcję Files.readString() z pakietu java.nio.file.Files).

Następnie wczytany tekst jest dzielony na słowa (funkcja split(), separator to biały znak), tworząc tablicę stringów. Zadaniem programu jest uruchomienie dla każdego stringu z tablicy wątku typu Callable, który zwraca napis postaci:

The length of string 'string': 6

Wątki Callable powinny być zaimplementowane z wykorzystaniem wyrażeń lambda.

Program główny pobiera string zwrócony przez wątek z odpowiedniego obiektu typu Future i wypisuje go na ekran.

Przykładowy wynik działania programu:

 

The length of string 'Lambda': 6
The length of string 'expression': 10
The length of string 'is': 2
The length of string 'a': 1
The length of string 'new': 3
The length of string 'and': 3
The length of string 'important': 9
The length of string 'feature': 7
The length of string 'of': 2
The length of string 'Java': 4
The length of string 'which': 5
The length of string 'was': 3
The length of string 'included': 8
The length of string 'in': 2
The length of string 'Java': 4
The length of string 'SE': 2
The length of string '8.': 2