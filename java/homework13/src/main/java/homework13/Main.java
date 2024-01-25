package homework13;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

record Book(String name, int releaseYear, String isbn) {
}

public class Main {
  public static void main(String[] args) {
    List<Book> bookList = List.of(
        new Book("The Fellowship of the Ring", 1954, "0395489318"),
        new Book("The Two Towers", 1954, "0345339711"),
        new Book("The Return of the King", 1955, "0618129111"),
        new Book("Harry Potter and the Philosopher's Stone", 1997, "0747532699"),
        new Book("To Kill a Mockingbird", 1960, "0061120081"),
        new Book("1984", 1949, "0451524934"),
        new Book("The Great Gatsby", 1925, "0743273567"),
        new Book("The Catcher in the Rye", 1951, "0316769487"));

    Map<String, String> isbnToTitleMap = bookList.stream()
        .collect(Collectors.toMap(Book::isbn, Book::name));

    System.out.println("Mapa ISBN to tytuł:");
    System.out.println(isbnToTitleMap);

    Map<Integer, Book> releaseYearToBookMap = bookList.stream()
        .collect(Collectors.toMap(Book::releaseYear, b -> b, (existing, replacement) -> existing));

    System.out.println("\nMapa roku wydania to książka:");
    System.out.println(releaseYearToBookMap);

    Map<Integer, List<Book>> releaseYearToBooksListMap = bookList.stream()
        .collect(Collectors.groupingBy(Book::releaseYear));

    System.out.println("\nMapa roku wydania to lista książek:");
    System.out.println(releaseYearToBooksListMap);
  }
}
