package multithreading;

public class Synchronization {

    static long bankBalance = 1_00_00_00_00_000L;


    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> add(1_00_000)); // add 1L
        Thread t2 = new Thread(() -> add(50_000)); // add 50k

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(bankBalance);
    }

    public static void add(long valueToAdd) {
        bankBalance += valueToAdd;
    }




    /* Suganthy Gold & Diamonds
        Bill - 1_00_000

        UPI / Bank Transfer

        Shantanu's Bank Account: 1_50_000
        Suganthy's Merchant Account: 10^(11)
        Rohan - Who is depositing 50k in Suganthy's account

        Op 1 - Shantanu's Account - Debit
            1. Read Bank balance
            2. Deduct 1_00_000 from bank balance
            3. Update new bank balance

        Op 2 - Suganthy's Account - Credit
            1. Read Bank balance
            2. Add 1_00_000 to bank balance
            3. Update new bank balance

        Op 3 -

        Started at the same time

            T1                 T3
    0      Read - 10^(11)  Read - 10^(11)
    1      10^(11) + 1L    10^(11) + 50k
    2      10^(11) + 1L    10^(11) + 1L

    Expected output = 10^(11) + 1L  + 50k
    Real Output = 10^(11) + 1L  or 10^(11) + 50k

    What if make a situation that
    if any thread is reading/writing the bank balance
    then no other thread must be allowd to read / write bak balance


     */

}
