package Core.Listener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

public abstract class MainListener extends Listener {

    @Override
    public void onFrame(Controller controller){
        if(!limitFrameRate(controller.frame())){
            return;
        }
        action(controller.frame());
    }

    public abstract void action(Frame frame);

    /**
     * Limit the CallRate to ~30cps
     * @param frame Leap Motion Frame
     * @return false if you jump the frame, true if you don't
     */
    protected final boolean limitFrameRate(Frame frame){
        return frame.id() % 2 != 0 && frame.isValid();
    }

    public abstract boolean isActive();

}
