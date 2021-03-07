
package Core.Listener;

import Core.Gesture.Finger.FingerCurveCalculator;
import Core.Gesture.GestureMatcher;
import Core.Gesture.HandPoses.HandType;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Gesture.Matrix.Structure.IDefineStructure;
import com.leapmotion.leap.*;

import java.util.Map;

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
