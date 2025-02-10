package lab2;

public class Race2 {

    public static void main(String[] args) {
        Counter cnt = new Counter(0);
        count it = new count(cnt);
        DThread dt = new DThread(cnt);

        it.start();
        dt.start();

        try {
            it.join();
            dt.join();
        } catch (InterruptedException ie) {
        }

        System.out.println("value: " + cnt.value());
    }
}

class Semafor {

    private boolean _stan = true;
    private int _czeka = 0;

    public Semafor() {
    }

    public synchronized void P() {
        while (!_stan) {
            try {
                _czeka++;
                wait();
                _czeka--;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        _stan = false;
    }

    public synchronized void V() {
        _stan = true;
        if (_czeka > 0) {
            notify();
        }
    }
}

class Counter {

    private int _val;
    private final Semafor semafor = new Semafor();

    public Counter(int n) {
        _val = n;
    }

    public void inc() {
        semafor.P();
        try {
            _val++;
        } finally {
            semafor.V();
        }
    }

    public void dec() {
        semafor.P();
        try {
            _val--;
        } finally {
            semafor.V();
        }
    }

    public int value() {
        return _val;
    }
}

class count extends Thread {

    final private Counter counter;

    public count(Counter c) {
        counter = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1_000_000; ++i) {
            counter.inc();
        }
    }
}

class DThread extends Thread {

    final private Counter counter;

    public DThread(Counter c) {
        counter = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1_000_000; ++i) {
            counter.dec();
        }
    }
}
