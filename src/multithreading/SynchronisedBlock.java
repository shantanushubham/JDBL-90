package multithreading;

public class SynchronisedBlock {

    public static String message = "";
    public static final Object temp = new Object();

    public static void appendMessage(String text) {
        synchronized (temp) {
            message = message + text;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> appendMessage("Hello "));
        Thread t2 = new Thread(() -> appendMessage("World "));
        Thread t3 = new Thread(() -> appendMessage("GeeksForGeeks "));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final Message: " + message);
    }
}
