package Core.Listener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

public abstract class MainListener extends Listener {
    public abstract void onConnect(Controller controller);
    public abstract void onFrame(Controller controller);

    /**
     * Limit the CallRate to ~30cps
     * @param frame Leap Motion Frame
     * @return false if you jump the frame, true if you don't
     */
    public boolean limitFrameRate(Frame frame){
        return frame.id() % 2 != 0 && frame.isValid();
    }
}
