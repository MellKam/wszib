package homework8;

public class Main {
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new Error("usage: <gridSize> <antCount>");
    }
    int gridSize = Integer.parseInt(args[0]);
    int antCount = Integer.parseInt(args[1]);

    AntSimulation antSimulation = new AntSimulation(gridSize, antCount);
    antSimulation.start();
  }
}