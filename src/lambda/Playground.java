package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Playground {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1);
//        list.forEach((el) -> System.out.println((el + 2) * 2));

        int ans = list.stream().collect(() -> list.size(), (el1, el2) -> System.out.println(el1 + el2),
                (el1, el2) -> System.out.println(el1 + el2));
        System.out.println(ans);

        AtomicInteger count = new AtomicInteger(10);

        Runnable r = () -> {
            System.out.println(count.incrementAndGet());
        };}
}
