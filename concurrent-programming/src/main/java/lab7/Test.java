package lab7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        int[] threadCounts = {10, 20, 50, 100};

        System.out.println("FineGrainedList Performance:");
        for (int threadCount : threadCounts) {
            FineGrainedList fineList = new FineGrainedList();
            for (int i = 0; i < 1000; i++) {
                fineList.add(i);
            }

            long startTime = System.nanoTime();
            runTest(threadCount, fineList);
            long endTime = System.nanoTime();

            System.out.printf("Threads: %d, Time: %.3f ms%n", threadCount, (endTime - startTime) / 1_000_000.0);
        }

        System.out.println("\nSingleLockList Performance:");
        for (int threadCount : threadCounts) {
            SingleLockList singleLockList = new SingleLockList();
            for (int i = 0; i < 1000; i++) {
                singleLockList.add(i);
            }

            long startTime = System.nanoTime();
            runTest(threadCount, singleLockList);
            long endTime = System.nanoTime();

            System.out.printf("Threads: %d, Time: %.3f ms%n", threadCount, (endTime - startTime) / 1_000_000.0);
        }
    }

    private static void runTest(int threadCount, Object list) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    if (list instanceof FineGrainedList) {
                        FineGrainedList l = (FineGrainedList) list;
                        l.add(j);
                        l.contains(j);
                        l.remove(j);
                    } else if (list instanceof SingleLockList) {
                        SingleLockList l = (SingleLockList) list;
                        l.add(j);
                        l.contains(j);
                        l.remove(j);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}

class SingleLockList {

    static class Node {

        Object value;
        Node next;

        Node(Object value) {
            this.value = value;
        }
    }

    private final Node head = new Node(null);
    private final ReentrantLock lock = new ReentrantLock();

    public boolean contains(Object o) {
        lock.lock();
        try {
            Node current = head.next;
            while (current != null) {
                if (current.value.equals(o)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean add(Object o) {
        lock.lock();
        try {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(o);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(Object o) {
        lock.lock();
        try {
            Node current = head;
            while (current.next != null) {
                if (current.next.value.equals(o)) {
                    current.next = current.next.next;
                    return true;
                }
                current = current.next;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}

class FineGrainedList {

    static class Node {

        Object value;
        Node next;
        final ReentrantLock lock = new ReentrantLock();

        Node(Object value) {
            this.value = value;
        }
    }

    private final Node head = new Node(null);

    public boolean contains(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Null values not allowed");
        }
        Node current = head;
        current.lock.lock();
        try {
            while (current.next != null) {
                Node next = current.next;
                next.lock.lock();
                try {
                    if (next.value.equals(o)) {
                        return true;
                    }
                    current.lock.unlock();
                    current = next;
                } finally {
                    if (current != next) {
                        next.lock.unlock();
                    }
                }
            }
        } finally {
            current.lock.unlock();
        }
        return false;
    }

    public boolean add(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Null values not allowed");
        }
        Node current = head;
        current.lock.lock();
        try {
            while (current.next != null) {
                current.lock.unlock();
                current = current.next;
                current.lock.lock();
            }
            current.next = new Node(o);
            return true;
        } finally {
            current.lock.unlock();
        }
    }

    public boolean remove(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Null values not allowed");
        }
        Node current = head;
        current.lock.lock();
        try {
            while (current.next != null) {
                Node next = current.next;
                next.lock.lock();
                try {
                    if (next.value.equals(o)) {
                        current.next = next.next;
                        return true;
                    }
                    current.lock.unlock();
                    current = next;
                } finally {
                    if (current != next) {
                        next.lock.unlock();
                    }
                }
            }
        } finally {
            current.lock.unlock();
        }
        return false;
    }
}
