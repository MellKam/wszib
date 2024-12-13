package streams;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    double avg = List.of(45, 39, 17, 25, 10, 4).stream()
      .filter(n -> n % 10 != 9 && n % 10 != 7)
      .mapToInt(n -> n)
      .average()
      .getAsDouble();
    System.err.println(avg);

    List.of(3, 10, 9, 4).stream()
      .map(n -> n * n + 9)
      .filter(n -> {
        int temp = n;
        while (temp > 0) {
          if (temp % 10 == 9) return false;
          temp /= 10;
        }
        return true;
      })
      .forEach(System.out::println);

    List.of("aa", "cy", "b", "yycd", "c").stream()
      .map(s -> s + "y")
      .filter(s -> !s.contains("yy"))
      .forEach(System.out::println);
  }
}
