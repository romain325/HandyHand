package Core.Daemon.Runner;

import com.leapmotion.leap.Controller;

public abstract class ControllerRunner implements Runnable {
    private final Controller controller = new Controller();
    public Controller getController(){ return controller; }
}
