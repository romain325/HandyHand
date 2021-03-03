package Core.Gesture.HandPoses;

import Core.Gesture.Finger.FingerState;
import Core.Gesture.Finger.FingerStateRecognizer;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;

import java.util.Map;

/**
 * A class to recognize some gesture of a Hand
 */
public class HandRecognizer {
    /**
     * The FingerCurveCalculator that we use in this class
     */
    private FingerStateRecognizer fingerStateRecognizer = new FingerStateRecognizer();

    /**
     * A method to know if the Hand is close or not
     * @param hand The Hand that we want the information of
     * @return True if the Hand is close, false otherwise
     */
    public boolean isHandClosed(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        for (Map.Entry<Finger.Type, FingerState> entry : fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() != FingerState.BENDING && entry.getKey() != Finger.Type.TYPE_THUMB) return false;
        }

        return true;
    }

    /**
     * A method to know if the Hand is open or not
     * @param hand The Hand that we want the information of
     * @return True if the Hand is open, false otherwise
     */
    public boolean isHandOpen(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() != FingerState.OUT) return false;
        }

        return true;
    }

    /**
     * A method to know if the Hand is doing scissors or not
     * @param hand The Hand that we want the information of
     * @return True if the Hand is doing scissors, false otherwise
     */
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

    /**
     * A method to know if the Hand is doing a fuck or not
     * @param hand The Hand that we want the information of
     * @return True if the Hand is doing a fuck, false otherwise
     */
    public boolean isHandFuck(Hand hand){
        if(hand == null || !hand.isValid()) return false;

        Map<Finger.Type,FingerState> states =  fingerStateRecognizer.getFingersState(hand);

        if(states.get(Finger.Type.TYPE_INDEX) != FingerState.BENDING) return false;
        if(states.get(Finger.Type.TYPE_MIDDLE) != FingerState.OUT) return false;
        if(states.get(Finger.Type.TYPE_RING) != FingerState.BENDING) return false;
        if(states.get(Finger.Type.TYPE_PINKY) != FingerState.BENDING) return false;

        return true;
    }

    /**
     * A method to know the number of Finger that are out of the Hand
     * @param hand The Hand that we want the information of
     * @return The number of Finger that are out of the Hand
     */
    public int countHandFingersOut(Hand hand) {
        if(hand == null || !hand.isValid()) return -1;

        int i = 0;
        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() == FingerState.OUT) i++;
        }

        return i;
    }



}




