package singleton;

public class Kitchen {

    private static Kitchen kitchen = null;

    public int burnerCount;

    private Kitchen(int burnerCount) {
        this.burnerCount = burnerCount;
    }

    public static Kitchen getInstance() {
        if (kitchen == null) {
            kitchen = new Kitchen(3);
        }
        return kitchen;
    }
}

/*
If there exists a class, for which, at any point in time, at-max only 1 object exists,
then it is called a Singleton class.
 */

class Demo {

    public static void main(String[] args) {
        Kitchen k1 = Kitchen.getInstance();
        Kitchen k2 = Kitchen.getInstance();

        System.out.println(k1.burnerCount);
        k2.burnerCount = 4;
        System.out.println(k1.burnerCount);
    }
}