package Core.Gesture;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;

import java.util.Map;

public class HandReconizer {

    public static boolean isHandClosed(Hand hand) {
        if(hand == null) return false;
        if(!hand.isValid()) return false;

        Map<Finger.Type,Boolean> fingersBend = FingerReconizer.fingersBend(hand);

        for (Map.Entry<Finger.Type,Boolean> entry: fingersBend.entrySet()) {
            if(!entry.getValue()) return false;
        }

        return false;
    }

    public static boolean isHandOpen(Hand hand) {
        if(hand == null) return false;
        if(!hand.isValid()) return false;

        Map<Finger.Type,Boolean> fingersOut = FingerReconizer.fingersOut(hand);

        for (Map.Entry<Finger.Type,Boolean> entry: fingersOut.entrySet()) {
            if(!entry.getValue()) return false;
        }

        return false;
    }

    public static boolean isHandScissors(Hand hand) {
        if(hand == null) return false;
        if(!hand.isValid()) return false;

        Map<Finger.Type,Boolean> fingersOut = FingerReconizer.fingersOut(hand);
        Map<Finger.Type,Boolean> fingersBend = FingerReconizer.fingersBend(hand);

        if(!fingersBend.get(Finger.Type.TYPE_THUMB)) return false;
        if(!fingersOut.get(Finger.Type.TYPE_INDEX)) return false;
        if(!fingersOut.get(Finger.Type.TYPE_MIDDLE)) return false;
        if(!fingersBend.get(Finger.Type.TYPE_RING)) return false;
        if(!fingersBend.get(Finger.Type.TYPE_PINKY)) return false;

        return true;
    }

    //Count the number showed by the hand (with the fingers)
    public static int countHandFingersOut(Hand hand) {
        if(hand == null) return -1;
        if(!hand.isValid()) return -1;

        Map<Finger.Type,Boolean> fingersOut = FingerReconizer.fingersOut(hand);

        int i = 0;
        for (Map.Entry<Finger.Type,Boolean> entry: fingersOut.entrySet()) {
            if(entry.getValue()) i++;
        }

        return i;
    }

}




