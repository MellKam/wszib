package homework8;

import java.util.Random;

public class AntSimulation {
  private static final char EMPTY = '.';
  private static final char ANT = '8';

  private char[][] grid;
  private Thread[] ants;

  private final int gridSize;
  private final int antCount;
  private final Random random;

  private HashMap<int[], Thread>

  public AntSimulation(int gridSize, int antCount) {
    this.gridSize = gridSize;
    this.antCount = antCount;
    this.random = new Random();
    this.grid = new char[gridSize][gridSize];
    this.ants = new Thread[antCount];

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        grid[i][j] = EMPTY;
      }
    }
    for (int i = 0; i < antCount; i++) {
      int randomX = random.nextInt(gridSize);
      int randomY = random.nextInt(gridSize);
      grid[randomX][randomY] = ANT;
      ants[i] = createAnt(randomX, randomY);
    }
  }

  private Thread createAnt(int x, int y) {
    return new Thread(() -> {
      while (true) {
        moveAnt(x, y);
        sleepRandomTime();
      }
    });
  }

  static class NoAntToMoveException extends Exception {
    public NoAntToMoveException(int x, int y) {
      super(String.format("can't find ant on (%d, %d)", x, y));
    }
  }

  static class MoveOutOfBoundException extends Exception {
    public MoveOutOfBoundException(int x, int y) {
      super(String.format("ant can't move to (%d, %d)", x, y));
    }
  }

  private synchronized void moveAnt(int x, int y) {
    int newX = x + random.nextInt(3) - 1;
    int newY = y + random.nextInt(3) - 1;

    if (newX < 0 || newX >= gridSize || newY < 0 || newY >= gridSize) {

    }

    if (newX >= 0 && newX < gridSize && newY >= 0 && newY < gridSize) {
      grid[x][y] = EMPTY;
      x = newX;
      y = newY;
      grid[x][y] = ANT;
      printGrid();
    } else {
      // Ant stays in place if the move is not valid
    }

    if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
      System.out.println("Ant died! Out of grid!");
    }
  }

  private void sleepRandomTime() {
    try {
      Thread.sleep(random.nextInt(1000));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private void printGrid() {
    System.out.println("Current state of the grid:");
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void startSimulation() {
    for (Thread ant : ants) {
      ant.start();
    }
  }
}
