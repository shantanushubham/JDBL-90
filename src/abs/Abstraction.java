package abs;

import java.util.ArrayList;
import java.util.List;

public class Abstraction {

    public static void main(String[] args) {
        EEngine eEngine = new EEngine();
        eEngine.startEngine();

        PEngine pEngine = new PEngine();
        pEngine.startEngine();

        HEngine hEngine = new HEngine();
        hEngine.startEngine();
    }

    public static List<Engine> getEngines() {
        List<Engine> engineList = new ArrayList<>();
//        engineList.add(new EEngine());
//        engineList.add(new PEngine());
//        engineList.add(new HEngine());
        return engineList;
    }
}

/*
An abstract class is just like a class.
- We cannot create an object of an abstract class.
- It can contain abstract & non-abstract members
 */
abstract class Engine {

    double torque;
    double energyOutput;
    long engineNumber;
    String type;
    boolean isAutomatic;

    abstract public void startEngine();

    abstract public void stopEngine();

    public void testFunction() {
        System.out.println("Test Function");
    }

}

class PEngine implements EngineI {

    @Override
    public void startEngine() {
        System.out.println("Petrol Engine Started");
    }

    @Override
    public void stopEngine() {
        System.out.println("Petrol Engine Stopped");
    }



}

class EEngine implements EngineI {

    @Override
    public void startEngine() {
        System.out.println("Electric Engine Started");
    }

    @Override
    public void stopEngine() {
        System.out.println("Electric Engine Stopped");
    }
}

class HEngine implements EngineI{

    @Override
    public void startEngine() {
        System.out.println("Hydrogen Engine Started");
    }

    @Override
    public void stopEngine() {
        System.out.println("Hydrogen Engine Stopped");
    }

}
