package homework2;

import java.util.Random;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    final int HISTOGRAM_BAR_COUNT = 10;

    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the number of iterations: ");
    int n = scanner.nextInt();

    if (n <= 0) {
      System.out.println("The number must be greater than 0");
      scanner.close();
      return;
    }

    Random random = new Random();
    int[] histogram = new int[HISTOGRAM_BAR_COUNT];

    for (int i = 0; i < n; i++) {
      int randomNumber = random.nextInt(HISTOGRAM_BAR_COUNT);
      histogram[randomNumber]++;
    }
    System.out.println("Histogram:");
    for (int i = 0; i < HISTOGRAM_BAR_COUNT; i++) {
      System.out.print(i + " ");
      for (int j = 0; j < histogram[i]; j++) {
        System.out.print("*");
      }
      System.out.println();
    }

    scanner.close();
  }
}
