package multithreading;

public class TestClass {

    public static void main(String[] args) throws InterruptedException {
        IncrementDemo incrementDemo = new IncrementDemo();
        Runnable r = () -> {
            for (int i = 0; i < 10000; i++) {
                incrementDemo.increment();
                ;
            }
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(incrementDemo.count);
    }
}

class IncrementDemo {

    public int count = 0;

    final Object myLock = new Object();

    public void increment() {
        synchronized (myLock) {
            count++;
        }
    }

}

// RAM: [   0    ][<lock-object>][][][][<new-lock-object>]
//        count             |                   myLock
//         T1 -----------------------              |
//         T2 --------------|