package Core.Daemon.Runner;

import com.leapmotion.leap.Controller;

/**
 * A class to define classes that implements Runnable and have a Controller for the LeapMotion
 */
public abstract class ControllerRunner implements Runnable {
    /**
     * The instance of Controller of the class
     */
    private final Controller controller = new Controller();

    /**
     * The getter of the instance of Controller of the class
     * @return The instance of Controller of the class
     */
    public Controller getController(){ return controller; }
}
