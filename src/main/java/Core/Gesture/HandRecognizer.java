package Core.Gesture;

import Core.Gesture.Finger.FingerState;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;

import java.util.Map;

public class HandRecognizer {
    private FingerStateRecognizer fingerStateRecognizer = new FingerStateRecognizer();

    public boolean isHandClosed(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        for (Map.Entry<Finger.Type, FingerState> entry : fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() != FingerState.BENDING) return false;
        }

        return true;
    }

    public boolean isHandOpen(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() != FingerState.OUT) return false;
        }

        return true;
    }

    public boolean isHandScissors(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        Map<Finger.Type,FingerState> states =  fingerStateRecognizer.getFingersState(hand);

        if(states.get(Finger.Type.TYPE_THUMB) == FingerState.OUT || states.get(Finger.Type.TYPE_THUMB) == FingerState.ERROR) return false;
        if(states.get(Finger.Type.TYPE_INDEX) != FingerState.OUT) return false;
        if(states.get(Finger.Type.TYPE_MIDDLE) != FingerState.OUT) return false;
        if(states.get(Finger.Type.TYPE_RING) != FingerState.BENDING) return false;
        if(states.get(Finger.Type.TYPE_PINKY) != FingerState.BENDING) return false;

        return true;
    }

    //Count the number showed by the hand (with the fingers)
    public int countHandFingersOut(Hand hand) {
        if(hand == null || !hand.isValid()) return -1;

        int i = 0;
        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() == FingerState.OUT) i++;
        }

        return i;
    }

}




