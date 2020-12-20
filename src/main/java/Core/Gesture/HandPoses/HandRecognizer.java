package Core.Gesture.HandPoses;

import Core.Gesture.Finger.FingerState;
import Core.Gesture.Finger.FingerStateRecognizer;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;

import java.util.Map;

/**
 * A class to recognize different form of the hand
 */
public class HandRecognizer {
    /**
     * The instance to calculate the different curves of the fingers
     */
    private FingerStateRecognizer fingerStateRecognizer = new FingerStateRecognizer();

    /**
     * A method to know if the hand is closed
     * @param hand The hand that we want the information
     * @return Return true if the hand is closed, false otherwise
     */
    public boolean isHandClosed(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        //Look if each fingers is bend, except the thumb
        for (Map.Entry<Finger.Type, FingerState> entry : fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() != FingerState.BENDING && entry.getKey() != Finger.Type.TYPE_THUMB) return false;
        }

        return true;
    }

    /**
     * A method to know if the hand is open
     * @param hand The hand that we want the information
     * @return Return true if the hand is open, false otherwise
     */
    public boolean isHandOpen(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        //Look if each fingers is out, except the thumb
        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() != FingerState.OUT) return false;
        }

        return true;
    }

    /**
     * A method to know if the hand is doing a scissors
     * @param hand The hand that we want the information
     * @return Return true if the hand is doing a scissors, false otherwise
     */
    public boolean isHandScissors(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        Map<Finger.Type,FingerState> states =  fingerStateRecognizer.getFingersState(hand);

        //Look if each fingers is bend, except the index and middle, who have to be out
        if(states.get(Finger.Type.TYPE_THUMB) == FingerState.OUT || states.get(Finger.Type.TYPE_THUMB) == FingerState.ERROR) return false;
        if(states.get(Finger.Type.TYPE_INDEX) != FingerState.OUT) return false;
        if(states.get(Finger.Type.TYPE_MIDDLE) != FingerState.OUT) return false;
        if(states.get(Finger.Type.TYPE_RING) != FingerState.BENDING) return false;
        if(states.get(Finger.Type.TYPE_PINKY) != FingerState.BENDING) return false;

        return true;
    }

    /**
     * A method to know if the hand is doing a fuck
     * @param hand The hand that we want the information
     * @return Return true if the hand is doing a fuck, false otherwise
     */
    public boolean isHandFuck(Hand hand){
        if(hand == null || !hand.isValid()) return false;

        Map<Finger.Type,FingerState> states =  fingerStateRecognizer.getFingersState(hand);

        //Look if each fingers is bend, except the middle, who have to be out
        if(states.get(Finger.Type.TYPE_INDEX) != FingerState.BENDING) return false;
        if(states.get(Finger.Type.TYPE_MIDDLE) != FingerState.OUT) return false;
        if(states.get(Finger.Type.TYPE_RING) != FingerState.BENDING) return false;
        if(states.get(Finger.Type.TYPE_PINKY) != FingerState.BENDING) return false;

        return true;
    }

    /**
     * Count the number showed by the hand
     * @param hand The hand that we want the information
     * @return The number showed by the hand
     */
    public int countHandFingersOut(Hand hand) {
        if(hand == null || !hand.isValid()) return -1;

        int i = 0;
        //Count the number of fingers whose out
        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() == FingerState.OUT) i++;
        }

        return i;
    }



}




