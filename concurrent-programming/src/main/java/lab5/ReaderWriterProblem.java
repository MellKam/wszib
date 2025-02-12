package lab5;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

public class ReaderWriterProblem {

    static class ReadersWritersSemaphore {

        private final Semaphore writeLock = new Semaphore(1, true);
        private final AtomicInteger readersCount = new AtomicInteger(0);
        private final boolean prioritizeWriters;

        public ReadersWritersSemaphore(boolean prioritizeWriters) {
            this.prioritizeWriters = prioritizeWriters;
        }

        public void startReading() throws InterruptedException {
            if (prioritizeWriters) {
                writeLock.acquire();
                writeLock.release();
            }
            if (readersCount.incrementAndGet() == 1) {
                writeLock.acquire();
            }
        }

        public void finishReading() {
            if (readersCount.decrementAndGet() == 0) {
                writeLock.release();
            }
        }

        public void startWriting() throws InterruptedException {
            writeLock.acquire();
        }

        public void finishWriting() {
            writeLock.release();
        }
    }

    static class ReadersWritersCondition {

        private final Lock lock = new ReentrantLock(true);
        private final Condition canRead = lock.newCondition();
        private final Condition canWrite = lock.newCondition();
        private int readers = 0;
        private int writers = 0;
        private boolean isWriting = false;
        private final boolean prioritizeWriters;

        public ReadersWritersCondition(boolean prioritizeWriters) {
            this.prioritizeWriters = prioritizeWriters;
        }

        public void startReading() throws InterruptedException {
            lock.lock();
            try {
                while (writers > 0 || isWriting || (prioritizeWriters && writers > 0)) {
                    canRead.await();
                }
                readers++;
            } finally {
                lock.unlock();
            }
        }

        public void finishReading() {
            lock.lock();
            try {
                if (--readers == 0) {
                    canWrite.signal();
                }
            } finally {
                lock.unlock();
            }
        }

        public void startWriting() throws InterruptedException {
            lock.lock();
            try {
                writers++;
                while (readers > 0 || isWriting) {
                    canWrite.await();
                }
                isWriting = true;
            } finally {
                lock.unlock();
            }
        }

        public void finishWriting() {
            lock.lock();
            try {
                isWriting = false;
                writers--;
                if (writers == 0) {
                    canRead.signalAll();
                } else {
                    canWrite.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class ReadersWritersFIFO {

        private final Lock lock = new ReentrantLock(true);
        private final Condition condition = lock.newCondition();
        private int readers = 0;
        private int writers = 0;
        private boolean isWriting = false;

        public void startReading() throws InterruptedException {
            lock.lock();
            try {
                while (writers > 0 || isWriting) {
                    condition.await();
                }
                readers++;
            } finally {
                lock.unlock();
            }
        }

        public void finishReading() {
            lock.lock();
            try {
                if (--readers == 0) {
                    condition.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }

        public void startWriting() throws InterruptedException {
            lock.lock();
            try {
                writers++;
                while (readers > 0 || isWriting) {
                    condition.await();
                }
                isWriting = true;
            } finally {
                lock.unlock();
            }
        }

        public void finishWriting() {
            lock.lock();
            try {
                isWriting = false;
                writers--;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] readersCounts = {10, 50, 100};
        int[] writersCounts = {1, 5, 10};
        boolean prioritizeWriters = true;
        StringBuilder resultCsv = new StringBuilder("readers,writers,library,execution_time\n");

        for (int readers : readersCounts) {
            for (int writers : writersCounts) {
                ReadersWritersSemaphore semaphores = new ReadersWritersSemaphore(prioritizeWriters);
                resultCsv.append(readers).append(",").append(writers).append(",semaphores,")
                        .append(testLibrary(semaphores, readers, writers)).append("\n");

                ReadersWritersCondition conditions = new ReadersWritersCondition(prioritizeWriters);
                resultCsv.append(readers).append(",").append(writers).append(",conditions,")
                        .append(testLibrary(conditions, readers, writers)).append("\n");

                ReadersWritersFIFO fifo = new ReadersWritersFIFO();
                resultCsv.append(readers).append(",").append(writers).append(",fifo,")
                        .append(testLibrary(fifo, readers, writers)).append("\n");
            }
        }

        System.out.println(resultCsv);
    }

    private static long testLibrary(Object library, int readersCount, int writersCount) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(readersCount + writersCount);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < readersCount; i++) {
            executor.submit(() -> {
                try {
                    if (library instanceof ReadersWritersSemaphore s) {
                        s.startReading();
                        Thread.sleep(50);
                        s.finishReading();
                    } else if (library instanceof ReadersWritersCondition c) {
                        c.startReading();
                        Thread.sleep(50);
                        c.finishReading();
                    } else if (library instanceof ReadersWritersFIFO f) {
                        f.startReading();
                        Thread.sleep(50);
                        f.finishReading();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < writersCount; i++) {
            executor.submit(() -> {
                try {
                    if (library instanceof ReadersWritersSemaphore s) {
                        s.startWriting();
                        Thread.sleep(100);
                        s.finishWriting();
                    } else if (library instanceof ReadersWritersCondition c) {
                        c.startWriting();
                        Thread.sleep(100);
                        c.finishWriting();
                    } else if (library instanceof ReadersWritersFIFO f) {
                        f.startWriting();
                        Thread.sleep(100);
                        f.finishWriting();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.currentTimeMillis() - startTime;
    }
}
