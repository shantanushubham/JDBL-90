package abs;

import java.util.List;

public class Playground {

    public static void main(String[] args) {
        List<Engine> list = Abstraction.getEngines();
        for (Engine e: list) {
            e.startEngine();
            e.stopEngine();
        }
    }
}
