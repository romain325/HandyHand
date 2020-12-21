package Core.Gesture.Finger;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import java.util.HashMap;
import java.util.Map;

/**
 * A class who recognize the state of the finger
 */
public class FingerStateRecognizer {
    /**
     * The instance to calculate the different curves of the fingers
     */
    private final FingerCurveCalculator fingerCurveCalculator = new FingerCurveCalculator();

    /**
     * Returns the state of each fingers of the hand
     * @param hand The hand that we want the information
     * @return A Map with the state of the fingers of the hand
     */
    public Map<Finger.Type, FingerState> getFingersState(Hand hand){
        Map<Finger.Type,FingerState> fingerStateMap = new HashMap<>();
        for (Finger finger: hand.fingers()) {
            fingerStateMap.put(finger.type(), getFingerState(finger));
        }
        return fingerStateMap;
    }

    /**
     * Returns the state of the finger
     * @param finger The finger that we want the information
     * @return The state of the finger
     */
    public FingerState getFingerState(Finger finger){
        FingerState state = getTipState(finger);
//        if(finger.type() == Finger.Type.TYPE_MIDDLE) System.out.println(state);
        if(state == getStartState(finger)){
//            if(finger.type() == Finger.Type.TYPE_MIDDLE) System.out.println(getStartState(finger));
            return state;
        }else{
//            if(finger.type() == Finger.Type.TYPE_MIDDLE) System.out.println(getStartState(finger));
            return FingerState.NORMAL;
        }
    }

    /**
     * Returns the state of the fingertip
     * @param finger The finger that we want the information
     * @return The state of the fingertip
     */
    public FingerState getTipState(Finger finger){
//        if(finger.type() == Finger.Type.TYPE_MIDDLE) System.out.println(fingerCurveCalculator.fingerTip(finger));
        return recognizeState(fingerCurveCalculator.fingerTip(finger));
    }

    /**
     * Returns the state of the beginning of the finger
     * @param finger The finger that we want the information
     * @return The state of the beginning of the finger
     */
    public FingerState getStartState(Finger finger){
//        if(finger.type() == Finger.Type.TYPE_MIDDLE) System.out.println(fingerCurveCalculator.fingerStart(finger));
        return recognizeState(fingerCurveCalculator.fingerStart(finger));
    }

    /**
     * Return the state based on the percentage of curve
     * @param f The percentage of curve
     * @return The state based on the percentage of curve
     */
    private FingerState recognizeState(float f){
        //If the curve is under 0, it's an error
        if(f < 0 || f > 100){
            return FingerState.ERROR;
        }
        //If the curve is between 0 and 15, the finger is out
        if(f < 15){
            return FingerState.OUT;
        }
        //If the curve is between 75 and 100, the finger is bend
        if (f > 75) {
            return FingerState.BENDING;
        }
        //Else, the finger is set to normal
        return FingerState.NORMAL;
    }

    /**
     * To get the instance of the class of the FingerCurveCalculator in that class
     * @return The instance of the class of the FingerCurveCalculator
     */
    public FingerCurveCalculator getFingerCurveCalculator() {
        return fingerCurveCalculator;
    }
}
