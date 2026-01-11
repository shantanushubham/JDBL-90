package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooling {

    /*
    You can use at max n number of threads at the same time.

    Create a fixed number of threads once and reuse them to
    execute many tasks.
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 100; i++) {
            int taskId = i;
            executorService.submit(() -> System.out.println("Task " + taskId +
                    " executed by " +
                    Thread.currentThread().getName()));
        }
        executorService.shutdown();
    }
}

// Tasks:    T1 T2 T3 T4 T5
// Threads         1   2

class NoThreadPool {
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            int taskId = i;
            Thread t = new Thread(() -> System.out.println("Task " + taskId +
                    " executed by " +
                    Thread.currentThread().getName()));
            t.start();
        }
    }
}


/*
1. If your task is CPU Bound (Calculations, Image Processing, Encryption, Sorting etc)
    - poolSize = noOfCores or noOfCores + 1
2. If it is IO Bound (DB calls, Network calls, File I/O, APIs)
    - poolSize = cores * (1 + waitTime / computeTime)
3. If it is mixed workload
 */
