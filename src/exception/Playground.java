package exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Playground {

    public static void main(String[] args) {
        call1();
        call2();
        try {
            compileTimeExceptionFunction();
        } catch (Exception e) {

        }
    }

    public static void call1() {
        try {
            System.out.println("Inside call 1");
            testFunction();
        } catch (RuntimeException e) {
            System.out.println("Solution 1");
        }
    }

    public static void call2() {
        try {
            System.out.println("Inside call 2");
            testFunction();
        } catch (RuntimeException e) {
            System.out.println("Solution 2: " + e.getMessage());
        }
    }

    public static void testFunction() {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        if (a < 0 || b < 0) {
            // throw an exception
            throw new RuntimeException("Please enter +ve values only!!");
        }
        System.out.println((a + b) / (a - b));
    }

    public static void compileTimeExceptionFunction() throws IOException {
        FileReader fileReader = new FileReader("src/exception/test.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            System.out.println(line);
            line = bufferedReader.readLine();
        }
    }

}

/*
There are 2 types of problems (Throwable) in programming
1. That cannot be solved - Error
2. That can be solved - Exception
    - Type1 - Runtime Exception
    - Type2 - Compile-time Exception
 */