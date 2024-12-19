package lab4;

class Sync {
  public int tura = 1;
}

class T1 extends Thread {
  private Sync sync;

  public T1(Sync sync) {
    this.sync = sync;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      synchronized (sync) {
        while (sync.tura != 1) {
          try {
            sync.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(1);
        sync.tura = 2;
        sync.notifyAll();
      }
    }
  }
}

class T2 extends Thread {
  private Sync sync;

  public T2(Sync sync) {
    this.sync = sync;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      synchronized (sync) {
        while (sync.tura != 2) {
          try {
            sync.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(2);
        sync.tura = 1;
        sync.notifyAll();
      }
    }
  }
}

public class Tury {
  public static void main(String[] args) {
    Sync sync = new Sync();
    T1 t1 = new T1(sync);
    T2 t2 = new T2(sync);
    t1.start();
    t2.start();
  }
}