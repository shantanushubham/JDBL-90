package multithreading;

public class CounterWithoutSync {

    static final Object myLock = new Object();

    static Integer count = 0;
    /*
        T1           T2
        Read = 0   Read = 0
        Inc 1        Inc 1
        Update         Update
     */

    // T1 - increment()
    // T2 - wait

//    synchronized static void increment() {
//        count++; // 1sec
//    }
    /*
        A synchronised function is used by only 1 thread at a time
     */

    static void increment() {
        synchronized (myLock) {
            count++;
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {

            // 1000 lines before

            for (int i = 0; i < 1000; i++) {
                increment();
            }

            // 1000 lines after
        });

        Thread t2 = new Thread(() -> {

            // 1000 lines before

            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });


        // 1000 lines after

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Count: "+count); // 2k sec
}
}
