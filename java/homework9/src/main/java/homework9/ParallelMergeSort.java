package homework9;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {
  private static final int THRESHOLD = 1000;
  private double[] array;
  private int low;
  private int high;

  public ParallelMergeSort(double[] array, int low, int high) {
    this.array = array;
    this.low = low;
    this.high = high;
  }

  @Override
  protected void compute() {
    if (high - low <= THRESHOLD) {
      Arrays.sort(array, low, high);
    } else {
      int mid = low + (high - low) / 2;

      ParallelMergeSort leftTask = new ParallelMergeSort(array, low, mid);
      ParallelMergeSort rightTask = new ParallelMergeSort(array, mid, high);

      invokeAll(leftTask, rightTask);
      merge(mid);
    }
  }

  private void merge(int mid) {
    int size = high - low;
    double[] temp = new double[size];
    int i = low, j = mid, k = 0;

    while (i < mid && j < high) {
      if (array[i] < array[j]) {
        temp[k++] = array[i++];
      } else {
        temp[k++] = array[j++];
      }
    }

    while (i < mid) {
      temp[k++] = array[i++];
    }

    while (j < high) {
      temp[k++] = array[j++];
    }

    System.arraycopy(temp, 0, array, low, size);
  }
}
