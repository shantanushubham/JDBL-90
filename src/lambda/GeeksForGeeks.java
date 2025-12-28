package lambda;

import java.util.function.Predicate;

@FunctionalInterface
public interface GeeksForGeeks {

    void teach();

}

@FunctionalInterface
interface Add {

    int add(int x);

}

@FunctionalInterface
interface Check {

    boolean check();
}

//class JDBL90 implements GeeksForGeeks {
//
//    @Override
//    public void teach() {
//        System.out.println("Teaching JDBL-90");
//    }
//}

class Demo {
    public static void main(String[] args) {

        // syntax
        /*
            ( argument-list ) -> {
                function body
            };
         */
        GeeksForGeeks jdbl = () ->
                System.out.println("Teaching JDBL-90");
        GeeksForGeeks dsa = () ->
                System.out.println("Teaching DSA-90");
        jdbl.teach();
        dsa.teach();


        int a = 5;
        Add z = x -> a + x;

        z.add(10);
        String name = "GeeksForGeeks";
        Check c = () -> name.startsWith("Geeks");
        System.out.println(c.check());

        Predicate<String> p = t ->
                t.startsWith("Geeks");
        System.out.println(p.test(name));

    }
}

/*
Task:
Create an interface called as Check that has
1 abstract function called check() that returns
a boolean

This function should be used when we want to
know ifa statement returns a true/false value

Eg: check() {
    return 4 < 5; // returns true
}
 */