package homework3;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);

      System.out.print("Enter the size of the board (nXn): ");
      int boardSize = Integer.parseInt(scanner.nextLine());

      Board board = new Board(boardSize, boardSize, 0.3);

      while (true) {
        board.print();
        System.out.println("Press Enter to continue or type 'Q' to quit...");

        String userInput = scanner.nextLine().trim();
        if (userInput.equalsIgnoreCase("Q")) {
          System.out.println("Exiting the game.");
          break;
        }

        board.nextGeneration();
      }

      scanner.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
