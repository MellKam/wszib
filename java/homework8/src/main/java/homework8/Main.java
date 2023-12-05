package homework8;

// public class Main {
// public static void main(String[] args) {
// if (args.length < 2) {
// System.out.println("Usage: <boardSize> <antCount>");
// System.exit(1);
// }

// int boardSize = Integer.parseInt(args[0]);
// int antCount = Integer.parseInt(args[1]);

// Board board = new Board(boardSize);
// Ant[] ants = new Ant[antCount];

// for (int i = 0; i < antCount; i++) {
// ants[i] = new Ant(board);
// }
// }
// }

public class Main {
  public static void main(String[] args) {
    int gridSize = Integer.parseInt(args[0]);
    int antCount = Integer.parseInt(args[1]);

    AntSimulation antSimulation = new AntSimulation(gridSize, antCount);
    antSimulation.startSimulation();
  }
}