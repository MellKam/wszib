package lab2;

public class Race2 {
  public static void main(String[] args) {
    Counter cnt = new Counter(0);
    IThread it = new IThread(cnt);
    DThread dt = new DThread(cnt);

    it.start();
    dt.start();

    try {
      it.join();
      dt.join();
    } catch (InterruptedException ie) {
    }

    System.out.println("value=" + cnt.value());
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

class IThread extends Thread {
  private Counter _cnt;

  public IThread(Counter c) {
    _cnt = c;
  }

  public void run() {
    for (int i = 0; i < 1000000; ++i) {
      _cnt.inc();
    }
  }
}

class DThread extends Thread {
  private Counter _cnt;

  public DThread(Counter c) {
    _cnt = c;
  }

  public void run() {
    for (int i = 0; i < 1000000; ++i) {
      _cnt.dec();
    }
  }
}
