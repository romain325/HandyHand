package Core.Listener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

/**
 * The abstract method to define a listener which will be used to interact with the event system
 */
public abstract class MainListener extends Listener {

    protected boolean isActive;

    /**
     * Listener onFrame event
     * @param controller
     */
    @Override
    public void onFrame(Controller controller){
        if(!limitFrameRate(controller.frame())){
            return;
        }
        action(controller.frame());
    }

    /**
     * action to perform onFrame
     * @param frame current frame
     */
    public abstract void action(Frame frame);

    /**
     * Limit the CallRate to ~30cps
     * @param frame Leap Motion Frame
     * @return false if you jump the frame, true if you don't
     */
    protected final boolean limitFrameRate(Frame frame){
        return frame.id() % 2 == 0 && frame.isValid();
    }

    /**
     * is the current listener seeing the right thing
     * @return true if the config is active, false if not
     */
    public boolean isActive(){
        return isActive;
    }

}
