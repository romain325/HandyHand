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

    public void addListener(MainListener[] listeners, Script script) {
        if(listeners.length <= 0){
            return;
        }
        this.listeners.put(script, new HashSet<>(Arrays.asList(listeners)));
        for (MainListener lis : listeners) {
            if(lis == null) continue;
            controller.addListener(lis);
        }
    }

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
