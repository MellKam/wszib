package homework9;

import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    int arraySize = 1000000;

    double[] arrayToSort = new double[arraySize];
    for (int i = 0; i < arraySize; i++) {
      arrayToSort[i] = Math.random();
    }

    ForkJoinPool forkJoinPool = new ForkJoinPool();
    ParallelMergeSort task = new ParallelMergeSort(arrayToSort, 0, arraySize);
    forkJoinPool.invoke(task);

    forkJoinPool.close();

    double min = Arrays.stream(arrayToSort).min().getAsDouble();
    double max = Arrays.stream(arrayToSort).max().getAsDouble();

    if (min != arrayToSort[0] || max != arrayToSort[arrayToSort.length - 1]) {
      throw new Error("Incorrect sorting");
    } else {
      System.out.print("Correctly sorted");
    }
  }
}
