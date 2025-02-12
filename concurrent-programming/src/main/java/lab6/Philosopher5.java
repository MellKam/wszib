
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher5 {

    public static void main(String[] args) {
        final int numberOfPhilosophers = 5;

        Fork[] forks = new Fork[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork();
        }

        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numberOfPhilosophers]);
            philosophers[i].start();
        }

        long startTime = System.currentTimeMillis();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\nStatistics:");
        for (Philosopher philosopher : philosophers) {
            System.out.println(philosopher.getName() + " ate " + philosopher.getCounter() + " times.");
        }

        for (Philosopher philosopher : philosophers) {
            philosopher.interrupt();
        }

        System.out.println("\nProgram execution time: " + (endTime - startTime) + " ms");
    }
}

class Fork {

    private final Lock lock = new ReentrantLock();

    public void pickUp() {
        lock.lock();
    }

    public void putDown() {
        lock.unlock();
    }
}

class Philosopher extends Thread {

    private final Fork leftFork;
    private final Fork rightFork;
    private int counter = 0;

    public Philosopher(int id, Fork left, Fork right) {
        super("Philosopher-" + id);
        this.leftFork = left;
        this.rightFork = right;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(getName() + " is thinking.");
                Thread.sleep((int) (Math.random() * 100));

                leftFork.pickUp();
                System.out.println(getName() + " picked up the left fork.");
                rightFork.pickUp();
                System.out.println(getName() + " picked up the right fork.");

                System.out.println(getName() + " is eating.");
                Thread.sleep((int) (Math.random() * 100));
                counter++;
                System.out.println(getName() + " finished eating " + counter + " times.");

                leftFork.putDown();
                rightFork.putDown();
                System.out.println(getName() + " put down the forks.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
