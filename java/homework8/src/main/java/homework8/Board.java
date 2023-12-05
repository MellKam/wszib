package homework8;

public class Board {
  private static final char EMPTY = '.';
  private static final char ANT = '8';

  private char[][] board;
  private final int size;
  private int occupiedCells = 0;

  public Board(int size) {
    this.size = size;
    this.board = new char[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = EMPTY;
      }
    }
  }

  public int getSize() {
    return size;
  }

  public void placeAnt(int x, int y) {

  }

  public boolean isValidMove(int x, int y) {
    return x >= 0 && x < this.size && y >= 0 && y < this.size;
  }

  public synchronized void moveAnt(int oldX, int oldY, int newX, int newY)
      throws MoveOutOfBoundException, NoAntToMoveException {
    if (board[oldX][oldY] == EMPTY) {
      throw new NoAntToMoveException(oldX, oldY);
    }
    if (newX < 0 && newX >= this.size && newY < 0 && newY >= this.size) {
      System.out.printf("Ant at (%d, %d) dies, new ant at (%d, %d) appears", oldX, oldY);
      this.print();

      throw new MoveOutOfBoundException(newX, newY);
    }

    if (board[newX][newY] == ANT) {
      return;
    }

    board[oldX][oldY] = EMPTY;
    board[newX][newY] = ANT;
  }

  void print() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}
