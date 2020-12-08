package Core.Interaction;

import Core.Listener.MainListener;
import Core.Script.Script;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

import java.util.*;

public class Interaction implements Runnable{
    private Controller controller = new Controller();
    private Map<Script, Set<MainListener>> listeners = new HashMap<>();

    protected final boolean limitFrameRate(Frame frame){
        return frame.id() % 2 != 0 && frame.isValid();
    }

    /**
     * Add a new script with attached listeners
     * @param listeners Array of listener
     * @param script Script to execute
     * @throws IllegalArgumentException If argument are null
     */
    public void addListener(MainListener[] listeners, Script script) throws IllegalArgumentException {
        if(listeners.length <= 0 || script == null){
            throw new IllegalArgumentException("The parameters given aren't valid");
        }
        this.listeners.put(script, new HashSet<>(Arrays.asList(listeners)));
        for (MainListener lis : listeners) {
            if(lis == null) continue;
            controller.addListener(lis);
        }
    }

    /**
     * Action to run on x call
     */
    @Override
    public void run() {
        for (Script s : listeners.keySet() ) {
            boolean active = true;
            for (MainListener listener : listeners.get(s)) {
                active = active && listener.isActive();
            }
            if(active){
                s.run();
            }
        }
    }


}
