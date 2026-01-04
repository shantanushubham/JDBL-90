package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

    // Get the index
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int hashCode = Integer.parseInt(br.readLine());
        int n = Integer.parseInt(br.readLine());
        System.out.println(hashCode & (n - 1));
    }
}
