package collections;

public class Playground {

    public static void main(String[] args) {
        int oldCapacity = 4;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println(newCapacity);
    }
}
