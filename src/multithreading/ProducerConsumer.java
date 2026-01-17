package multithreading;

public class ProducerConsumer {

    public static void main(String[] args) {

        Buffer buffer = new Buffer();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                buffer.produce(i);
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                buffer.consume();
            }
        });
        t2.start();
    }
}

class Buffer {

    private int data;
    private boolean isAvailable = false;

    public synchronized void produce(int value) {
        while (isAvailable) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        data = value;
        isAvailable = true;
        System.out.println("Produced: " + value);
        notify();
    }

    public synchronized void consume() {
        while (!isAvailable) {
            try {
                System.out.println("Consumer waiting ...");
                wait(3_000);
                if (!isAvailable) {
                    System.out.println("Timeout! No item was produced");
                    return;
                }
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Consumed " + data);
        isAvailable = false;
        notify();
    }
}

/*
Start -> [3][4][5][6][7] -> End
 */
/*
Sequence (Assuming the consumer starts first)
1. Consumer thread starts
2. Consumer enters consume() -> acquires lock
3. Check condition [while (!isAvailable)] => true
4. Consumer calls wait()
    - Consumer enters waiting
    - Consumer releases the lock
5. Producer thread starts
6. Producer enters produce() -> acquires lock
7. Checks condition [while (!isAvailable)] => false
8. Producer produces the item | isAvailable = true
9. Producer calls notify()
    - Consumer moves from WAITING -> BLOCKED
10. Producer exits synchronized method -> releases lock
11. Consumer re-acquires lock
12. Consumer resumes after wait()
13. Rechecks condition [while (!isAvailable)] => false
14. Consumer consumes the item | isAvailable = false
15. Consumer calls notify()
16. Consumer exits synchronized -> releases lock
 */