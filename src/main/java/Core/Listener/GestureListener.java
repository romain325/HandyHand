
package Core.Listener;

import Core.Gesture.Finger.FingerCurveCalculator;
import Core.Gesture.GestureMatcher;
import Core.Gesture.HandPoses.HandType;
import com.leapmotion.leap.*;

import java.util.Map;

public class GestureListener extends MainListener {
    String key;
    HandType handType;

    private static boolean scriptExist(String key){
        return GestureMatcher.gestureExist(key);
    }

    private GestureListener(String key, HandType handType) {
        this.key = key;
        this.handType = handType;
    }

    public static GestureListener GestureListenerFactory(String form) {
        return GestureListener.GestureListenerFactory(form, HandType.BOTH);
    }
    public static GestureListener GestureListenerFactory(String form, HandType handType) {
        return new GestureListener(form, handType);
    }

    @Override
    public void action(Frame frame) {
        isActive = GestureMatcher.getResult(this.key,frame, this.handType);
    }
}
