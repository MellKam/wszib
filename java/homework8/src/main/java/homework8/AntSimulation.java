package homework8;

import java.util.Random;
import java.util.concurrent.*;

public class AntSimulation {
    private static final char EMPTY = '.';
    private static final char ANT = '8';

    private char[][] grid;
    private Thread[] ants;
    private ExecutorService executor;
    private final int gridSize;
    private final Random random;

    public AntSimulation(int gridSize, int antCount) {
        this.gridSize = gridSize;
        this.random = new Random();
        this.grid = new char[gridSize][gridSize];
        this.ants = new Thread[antCount];
        this.executor = Executors.newFixedThreadPool(antCount);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = EMPTY;
            }
        }

        for (int i = 0; i < antCount; i++) {
            int randomX = random.nextInt(gridSize);
            int randomY = random.nextInt(gridSize);
            if (grid[randomX][randomY] == EMPTY) {
                grid[randomX][randomY] = ANT;
                ants[i] = createAnt(randomX, randomY);
            }

        }
    }

    private Thread createAnt(int x, int y) {
        return new Thread(() -> {
            int antX = x;
            int antY = y;
            while (true) {
                int[] newCoords = moveAnt(antX, antY);
                antX = newCoords[0];
                antY = newCoords[1];
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private synchronized int[] moveAnt(int oldX, int oldY) {
        int newX = oldX + random.nextInt(3) - 1;
        int newY = oldY + random.nextInt(3) - 1;

        if (newX >= 0 && newX < gridSize && newY >= 0 && newY < gridSize) {
            if (grid[newX][newY] == EMPTY) {
                grid[oldX][oldY] = EMPTY;
                grid[newX][newY] = ANT;
                System.out.printf("Ant at (%d, %d) moves to (%d, %d):\n", oldX, oldY, newX, newY);
                printGrid();
                return new int[] { newX, newY };
            }
            return new int[] { oldX, oldY };
        }

        System.out.printf("Ant at (%d, %d) dies new ant at (%d, %d):\n", oldX, oldY, newX, newY);
        grid[oldX][oldY] = EMPTY;

        int randomX = random.nextInt(gridSize);
        int randomY = random.nextInt(gridSize);

        while (grid[randomX][randomY] == ANT) {
            randomX = random.nextInt(gridSize);
            randomY = random.nextInt(gridSize);
        }
        grid[randomX][randomY] = ANT;
        printGrid();
        return new int[] { randomX, randomY };
    }

    private void printGrid() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void start() {
        for (Thread ant : ants) {
            executor.execute(ant);
        }
        executor.shutdown();
    }
}
