package homework8;

import java.util.Random;

class Ant implements Runnable {
  private int x;
  private int y;
  private final Board board;
  private final Random random;

  public Ant(Board board) {
    this.board = board;
    new Thread();
    this.random = new Random();
  }

  @Override
  public void run() {
    this.x = random.nextInt(board.getSize());
    this.y = random.nextInt(board.getSize());

    new Thread(() -> {
      while (true) {
        int newX = this.x + random.nextInt(3) - 1;
        int newY = this.y + random.nextInt(3) - 1;

        try {
          this.board.moveAnt(this.x, this.y, newX, newY);
          Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      this.run();
    });
  }
}