package multithreading;

public class TicketSystem implements Runnable {

    public static Thread myMainThread;
    public static TicketSystem ticketSystem;

    @Override
    public void run() {
        TicketBooking ticketBooking = new TicketBooking();
        Thread bookingThread = new Thread(ticketBooking);

        System.out.println("State after creating bookingThread: " + bookingThread.getState());

        bookingThread.start();
        System.out.println("State after starting bookingThread: " + bookingThread.getState());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted - TicketSystem");
        }

        System.out.println("State after sleeping bookingThread: " + bookingThread.getState());

        try {
            bookingThread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted - TicketSystem");
        }

        System.out.println("State after bookingThread finishes: " + bookingThread.getState());

    }

    public static void main(String[] args) {
        ticketSystem = new TicketSystem();
        myMainThread = new Thread(ticketSystem);

        System.out.println("State after creating myMainThread: " + myMainThread.getState());

        myMainThread.start();

        System.out.println("State after starting myMainThread: " + myMainThread.getState());

    }


}

class TicketBooking implements Runnable {


    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException exception) {
            System.out.println("Thread Interrupted - TicketBooking");
        }
        System.out.println("State of bookingThread while myMainThread is waiting: " +
                TicketSystem.myMainThread.getState());
    }
}

class Demo {


}
