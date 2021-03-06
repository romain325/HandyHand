package Core.Gesture.HandPoses;

import Core.Gesture.Finger.FingerCurveCalculator;
import Core.Gesture.Finger.FingerPositionCalculator;
import Core.Gesture.Finger.FingerState;
import Core.Gesture.Finger.FingerStateRecognizer;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

import java.util.Map;

/**
 * A class to recognize some gesture of a Hand
 */
public class HandRecognizer {
    /**
     * The instance to calculate the different states of the fingers
     */
    private FingerStateRecognizer fingerStateRecognizer = new FingerStateRecognizer();
    /**
     * The instance to calculate the different positions of the fingers
     */
    private FingerPositionCalculator fingerPositionCalculator = new FingerPositionCalculator();
    /**
     * The instance to calculate the different curves of the fingers
     */
    private FingerCurveCalculator fingerCurveCalculator = new FingerCurveCalculator();
    /**
     * The instance to calculate the different positions of the hands
     */
    private HandPositionCalculator handPositionCalculator = new HandPositionCalculator();

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

        //Look if each fingers is bend, except the index and middle, who have to be out
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

        //Look if each fingers is bend, except the middle, who have to be out
        if(states.get(Finger.Type.TYPE_INDEX) != FingerState.BENDING) {
            //If the thumb is hiding the index, values won't be correct
            if(new FingerStateRecognizer().getTipState(hand.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0))
                    == FingerState.OUT) {
                if(states.get(Finger.Type.TYPE_INDEX) != FingerState.NORMAL) return false;
            }
        }
        //When others fingers are bend, the middle one is note near of 0 percent of bending, but near of 50 percent
        if(states.get(Finger.Type.TYPE_MIDDLE) == FingerState.BENDING ||
                states.get(Finger.Type.TYPE_MIDDLE) == FingerState.ERROR) return false;
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
        //Count the number of fingers whose out
        for (Map.Entry<Finger.Type,FingerState> entry: fingerStateRecognizer.getFingersState(hand).entrySet()) {
            if(entry.getValue() == FingerState.OUT) i++;
        }

        return i;
    }

    /**
     * A function that says if the hand is doing a O with the index and the humb
     * @param hand The hand that we want to have informations
     * @return Return true is the hand is doing a O with the index and the humb, false otherwise
     */
    public boolean isHandOk(Hand hand) {
        if(hand == null || !hand.isValid()) return false;

        Finger thumb = hand.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);
        Finger index = hand.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);

        //If the end of the thumb and the end of the index are stick, the hand is doing a O
        return fingerPositionCalculator.isNextDistalSticks(thumb, index);
    }

    /**
     * A function that says if 2 hands are doing a heart with there index and thumb
     * @param hands The HandList which contains both hands
     * @return Return true if both hands are doing a heart with there index and thumb
     */
    public boolean isHandsHeart(HandList hands) {
        if(hands.count() != 2) return false;
        Hand hand1 = hands.get(0);
        Hand hand2 = hands.get(1);
        if(hand1 == null || !hand1.isValid() || hand2 == null || !hand2.isValid()) return false;

        Finger thumb1 = hand1.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);
        Finger thumb2 = hand2.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);

        //If the end of both thumb aren't stick
        if(!fingerPositionCalculator.isNextDistalSticks(thumb1, thumb2)) return false;

        Finger index1 = hand1.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger index2 = hand2.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);

        //If the end of intermediate bones of both index aren't stick
        if(!fingerPositionCalculator.isNextIntermediateIndexsSticks(index1, index2)) return false;

        //If both thumb aren't away from the index
        if(fingerCurveCalculator.thumbStickingIndex(thumb1) != 0) return false;
        if(fingerCurveCalculator.thumbStickingIndex(thumb2) != 0) return false;

        //Doesn't work enough to use it
/*
        FingerStateRecognizer fsr = new FingerStateRecognizer();
        if(fsr.getStartState(index1) != FingerState.OUT) {
            System.out.println("getStartState index1");
            return false;
        }
        if(fsr.getStartState(index2) != FingerState.OUT) {
            System.out.println("getStartState index2");
            return false;
        }

        if(fsr.getTipState(index1) != FingerState.NORMAL) {
            System.out.println("getTipState index1");
            return false;
        }
        if(fsr.getTipState(index2) != FingerState.NORMAL) {
            System.out.println("getTipState index2");
            return false;
        }
*/
        return true;
    }

    /**
     * A function that says if 2 hands are doing a O with there index and thumb
     * @param hands The HandList which contains both hands
     * @return Return true if both hands are doing a O with there index and thumb
     */
    public boolean isHandsO(HandList hands) {
        if(hands.count() != 2) return false;
        Hand hand1 = hands.get(0);
        Hand hand2 = hands.get(1);
        if(hand1 == null || !hand1.isValid() || hand2 == null || !hand2.isValid()) return false;

        Finger thumb1 = hand1.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);
        Finger thumb2 = hand2.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);

        //If the end of both thumb aren't stick
        if(!fingerPositionCalculator.isNextDistalSticks(thumb1, thumb2)) return false;

        Finger index1 = hand1.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger index2 = hand2.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);

        //If the end of both index aren't stick
        if(!fingerPositionCalculator.isNextDistalSticks(index1, index2)) return false;

        //If both thumb aren't away from the index
        if(fingerCurveCalculator.thumbStickingIndex(thumb1) != 0) return false;
        if(fingerCurveCalculator.thumbStickingIndex(thumb2) != 0) return false;

        if(fingerCurveCalculator.fingerTip(index1) > 35) return false;
        if(fingerCurveCalculator.fingerTip(index2) > 35) return false;

        return true;
    }

    /**
     * A function that says if 2 hands are doing the Jull sign
     * @param hands The HandList which contains both hands
     * @return Return true if both hands are doing the Jull sign
     */
    public boolean isHandsJull(HandList hands) {
        if(hands.count() != 2) return false;
        Hand hand1 = hands.get(0);
        Hand hand2 = hands.get(1);
        if(hand1 == null || !hand1.isValid() || hand2 == null || !hand2.isValid()) return false;

        Map<Finger.Type,Float> fingersCurve1 = fingerCurveCalculator.fingersCurve(hand1);
        Map<Finger.Type,Float> fingersCurve2 = fingerCurveCalculator.fingersCurve(hand2);

        //If index are out
        if(fingersCurve1.get(Finger.Type.TYPE_INDEX) > 60 ||
                fingersCurve2.get(Finger.Type.TYPE_INDEX) > 60) {
            System.out.println(Finger.Type.TYPE_INDEX);
//            System.out.println(fingersCurve1.get(Finger.Type.TYPE_INDEX));
            return false;
        }
        //If middle are out
        if(fingersCurve1.get(Finger.Type.TYPE_MIDDLE) > 60 ||
                fingersCurve2.get(Finger.Type.TYPE_MIDDLE) > 60) {
            System.out.println(Finger.Type.TYPE_MIDDLE);
//            System.out.println(fingersCurve1.get(Finger.Type.TYPE_MIDDLE));
            return false;
        }

        //If ring finger are bend
        if(fingersCurve1.get(Finger.Type.TYPE_RING) < 40 ||
                fingersCurve2.get(Finger.Type.TYPE_RING) < 40) return false;
        //If pinky finger aren't bend
        if(fingersCurve1.get(Finger.Type.TYPE_PINKY) < 40 ||
                fingersCurve2.get(Finger.Type.TYPE_PINKY) < 40) {
            System.out.println(Finger.Type.TYPE_PINKY);
            return false;
        }

        //If index and middle of first hand are stick
        Finger index1 = hand1.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger middle1 = hand1.fingers().fingerType(Finger.Type.TYPE_MIDDLE).get(0);
        if(!fingerPositionCalculator.isIndexMiddleStick(index1, middle1)) {
            System.out.println("isIndexMiddleStick 1");
            return false;
        }

        //If index and middle of second hand are stick
        Finger index2 = hand2.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger middle2 = hand2.fingers().fingerType(Finger.Type.TYPE_MIDDLE).get(0);
        if(!fingerPositionCalculator.isIndexMiddleStick(index2, middle2)) {
            System.out.println("isIndexMiddleStick 2");
            return false;
        }

        //If both hands are stick
        if(!handPositionCalculator.isHandsStick(hands)) {
            System.out.println("isHandsStick");
            return false;
        }

        //If thumbs are stick to the hand
        Finger thumb1 = hand1.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);
        Finger thumb2 = hand2.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);
        if(fingerCurveCalculator.thumbStickingIndex(thumb1) == 100) return false;
        if(fingerCurveCalculator.thumbStickingIndex(thumb2) == 100) return false;

        return true;
    }

    /**
     * To get the instance of the class of the FingerCurveCalculator in that class
     * @return The instance of the class of the FingerCurveCalculator
     */
    public FingerCurveCalculator getFingerCurveCalculator() {
        return fingerCurveCalculator;
    }

    /**
     * To get the instance of the class of the FingerStateRecognizer in that class
     * @return The instance of the class of the FingerStateRecognizer
     */
    public FingerStateRecognizer getFingerStateRecognizer() {
        return fingerStateRecognizer;
    }

    /**
     * To get the instance of the class of the FingerPositionCalculator in that class
     * @return The instance of the class of the FingerPositionCalculator
     */
    public FingerPositionCalculator getFingerPositionCalculator() {
        return fingerPositionCalculator;
    }

    /**
     * To get the instance of the class of the HandPositionCalculator in that class
     * @return The instance of the class of the HandPositionCalculator
     */
    public HandPositionCalculator getHandPositionCalculator() {
        return handPositionCalculator;
    }

}




