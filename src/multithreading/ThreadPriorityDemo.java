package multithreading;

public class ThreadPriorityDemo {

    // priority can range from 1 to 10;
    public static void main(String[] args) {

        Thread low = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Low priority thread");
            }
        });

        Thread high = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("High priority thread");
            }
        });

        low.setPriority(Thread.MIN_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        high.start();
    }
}
