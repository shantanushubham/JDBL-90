package multithreading;

public class Communication {

    /*
    Thread communication is the mechanism by which threads coordinate their execution
    and share state safely.

    It is used:
        - One thread must wait for another
        - Threads must be notified when a condition changes
        - When order of execution matters

    Example:
        - Processing data when data is available.

    Why do we need it?

    Because without thread communication
        - Threads will run independently
        - Busy waiting (wasting CPU)
        - Race conditions
        - Incorrect results

    With TC, we get
        - Coordination
        - CPU efficiency
        - Correct sequencing

    Core Java Communication methods:
    In the Object class, we have the following:
        - wait()            Releases lock and waits
        - notify()          Wakes one waiting thread
        - notifyAll()       Wakes all the waiting threads

    These functions must be called in a synchronized context else it will throw
        IllegalMonitorStateException

    How it works:
        - Thread A enters synchronized block -> gets lock/monitor
        - Thread A calls wait() -> Thread A releases the lock + sleeps
        - Thread B enters synchronized block
        - Thread B Calls notify()
        - Thread A wakes up and re-acquires lock.
     */

    static final Object lock = new Object();

    public static void main(String[] args) {

        notifyAllExample();
    }

    private static void notifyExample() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread 1 waiting ...");
                    lock.wait();
                    System.out.println("Thread 1 resumed!");
                } catch (InterruptedException ignored) {
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 2 notifying ...");
                lock.notify();
                System.out.println("Thread 2 has notified ...");
            }
        });

        t1.start();
        sleep(1_000);
        t2.start();
    }

    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    private static void notifyAllExample() {
        Runnable waitingTask = () -> {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting...");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + " resumed!");
                } catch (InterruptedException ignored) {}
            }
        };

        Thread t1 = new Thread(waitingTask, "Thread-1");
        Thread t2 = new Thread(waitingTask, "Thread-2");
        Thread t3 = new Thread(waitingTask, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        sleep(1_000);

        Thread notifier = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Notifier calling notifyAll()");
                lock.notifyAll();
                System.out.println("Notifier done");
            }
        });

        notifier.start();
    }
}

