
package Core.Listener;

import Core.Gesture.GestureMatcher;
import Core.Gesture.Matrix.Structure.IDefineStructure;
import com.leapmotion.leap.Frame;

public class GestureListener extends MainListener {
    GestureMatcher gestureMatcher;

    public GestureListener(IDefineStructure gestureStructure) {
        gestureMatcher=new GestureMatcher(gestureStructure);
    }

    @Override
    public void action(Frame frame) {
        isActive = gestureMatcher.getResult(frame);
    }
}
