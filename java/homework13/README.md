Napisz klasę Book opisującą książkę:

record Book(String name, int releaseYear, String isbn) { ... } Stwórz listę
książek, np:

List<Book> bookList = List.of( new Book("The Fellowship of the Ring", 1954,
"0395489318"), new Book("The Two Towers", 1954, "0345339711"), new Book("The
Return of the King", 1955, "0618129111"), ... ); Używając funkcji
Collectors.toMap() skonstruuj mapę:

której zbiorem kluczy będą numery isbn, a zbiorem wartości tytuły książek.
której zbiorem kluczy będą lata wydania, a zbiorem wartości obiekty typu Book.
Zastosuj odpowiednią funkcję mergującą do rozwiązania konfliktu kluczy (np.
pozostawiającą pierwotną wartość dla danego klucza). Wykorzystaj funkcję
Collectors.groupingBy() do stworzenia mapy, której zbiorem kluczy będą lata
wydania, a zbiorem wartości listy obiektów typu Book (książek wydanych w danym
roku).
