
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosophersWithButler {

    public static void main(String[] args) {
        int numberOfPhilosophers = 5;
        Fork[] forks = new Fork[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork();
        }

        Butler butler = new Butler(numberOfPhilosophers - 1);
        Philosopher[] philosophers = new Philosopher[numberOfPhilosophers];

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numberOfPhilosophers], butler);
            philosophers[i].start();
        }

        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\nStatistics:");
        for (Philosopher philosopher : philosophers) {
            System.out.println("Philosopher-" + philosopher.getName() + " ate " + philosopher.getEatCount() + " times.");
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

    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private final Butler butler;
    private int eatCount = 0;

    public Philosopher(int id, Fork leftFork, Fork rightFork, Butler butler) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.butler = butler;
    }

    @Override
    public void run() {
        try {
            while (eatCount < 20) {
                think();
                butler.requestPermission();
                leftFork.pickUp();
                System.out.println("Philosopher-" + id + " picked up the left fork.");
                rightFork.pickUp();
                System.out.println("Philosopher-" + id + " picked up the right fork.");
                eat();
                leftFork.putDown();
                System.out.println("Philosopher-" + id + " put down the left fork.");
                rightFork.putDown();
                System.out.println("Philosopher-" + id + " put down the right fork.");
                butler.releasePermission();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher-" + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 100));
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher-" + id + " is eating.");
        eatCount++;
        Thread.sleep((long) (Math.random() * 100));
        System.out.println("Philosopher-" + id + " has finished eating " + eatCount + " times.");
    }

    public int getEatCount() {
        return eatCount;
    }
}

class Butler {

    private final Semaphore semaphore;

    public Butler(int numberOfSpots) {
        this.semaphore = new Semaphore(numberOfSpots);
    }

    public void requestPermission() throws InterruptedException {
        semaphore.acquire();
    }

    public void releasePermission() {
        semaphore.release();
    }
}
