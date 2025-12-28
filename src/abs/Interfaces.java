package abs;

public class Interfaces {
}

/*
An interface is just like an abstract class, but
1. It only contains abstract members
2. All variables are static & final variables
3. All methods in interface are public by default
 */

interface EngineI {

    double torque = 0D;

    void startEngine();

    void stopEngine();

}
