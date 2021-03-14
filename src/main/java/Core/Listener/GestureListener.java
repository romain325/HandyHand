
package Core.Listener;

import Core.Gesture.GestureMatcher;
import Core.Gesture.Matrix.Structure.IDefineStructure;
import com.leapmotion.leap.Frame;

/**
 * Class that define a listener for gestures
 */
public class GestureListener extends MainListener {
    /**
     * Matcher that will compare gestures
     */
    GestureMatcher gestureMatcher;

    /**
     * Constructir of the gesture listener
     * @param gestureStructure the gesture that you will compare to
     */
    public GestureListener(IDefineStructure gestureStructure) {
        gestureMatcher=new GestureMatcher(gestureStructure);
    }

    /**
     * Method called on run of our demon
     * @param frame current frame
     */
    @Override
    public void action(Frame frame) {
        isActive = gestureMatcher.getResult(frame);
    }
}
