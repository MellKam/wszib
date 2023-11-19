package homework1;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    try {
      System.out.print("Enter an integer: ");
      int n = scanner.nextInt();

      if (n < 1) {
        System.out.println("The number specified must be greater than or equal to 1.");
        scanner.close();
        return;
      }

      System.out.println("Divisors of a number " + n + " :");

      for (int i = 1; i <= n; i++) {
        if (n % i == 0) {
          System.out.println(i);
        }
      }
    } catch (java.util.InputMismatchException e) {
      System.out.println("An invalid integer was specified.");
    }

    scanner.close();
  }
}
