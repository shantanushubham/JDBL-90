package multithreading.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(() -> {
            Thread.sleep(1_000);
            throw new RuntimeException();
//            return 10 + 20;
        });
        System.out.println("Doing some other work ...");
        try {
            Integer result = future.get();
            System.out.println("Result: " + result);
        } catch (RuntimeException e) {
            System.out.println("Runtime");
        } catch (ExecutionException e) {
            System.out.println("Execution");
        }

        executorService.shutdown();

    }

    /*
    Good:
    1. Represents a result
    2. get() blocks
    3. Can check status with isDone()
    4. Can cancel tasks

    Bad:
    1. Cannot attach callbacks
    2. No chaining
    3. Hard to compute multiple tasks
     */

    // A->B->Combine
    // We have CompletableFuture
}
