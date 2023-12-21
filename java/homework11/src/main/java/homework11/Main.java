package homework11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
  public static void main(String[] args) {
    try {
      String text = Files.readString(Paths.get("input.dat"));
      String[] words = text.split("\\s+");

      ExecutorService executorService = Executors.newFixedThreadPool(words.length);

      List<Future<String>> futures = new ArrayList<Future<String>>();

      for (String word : words) {
        Callable<String> task = () -> "The length of string '" + word + "': " + word.length();
        Future<String> future = executorService.submit(task);
        futures.add(future);
      }

      for (Future<String> future : futures) {
        try {
          System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      }

      executorService.shutdown();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
