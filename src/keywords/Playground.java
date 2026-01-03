package keywords;

import java.io.Serializable;

public class Playground {

    static {
        System.loadLibrary("nativedemo");
    }

    public static void main(String[] args) {
        Playground p = new Playground();
        System.out.println(p.add(3, 4));
    }

    public native int add(int a, int b);
}

class User implements Serializable {

    String username;
    transient String password;
    // Security information like passwords, tokens
    // Also used for derived and temporary data
    // For non-serializable fields

}
