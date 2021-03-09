package Core.Gesture.Finger;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to recognize the state of a Finger
 */
public class FingerStateRecognizer {
    /**
     * The instance to calculate the different curves of the fingers
     */
    private final FingerCurveCalculator fingerCurveCalculator = new FingerCurveCalculator();

    /**
     * A method to get the state of each fingers of a Hand
     * @param hand The Hand that we want the information of
     * @return A map with the state of each fingers and there type
     */
    public Map<Finger.Type, FingerState> getFingersState(Hand hand){
        Map<Finger.Type,FingerState> fingerStateMap = new HashMap<>();
        for (Finger finger: hand.fingers()) {
            fingerStateMap.put(finger.type(), getFingerState(finger));
        }
        return fingerStateMap;
    }

    /**
     * A method to get the state of a Finger
     * @param finger The Finger that we want the information of
     * @return The state of the Finger
     */
    public FingerState getFingerState(Finger finger){
        FingerState state = getTipState(finger);
        if(state == getStartState(finger)){
            return state;
        }else{
            return FingerState.NORMAL;
        }
    }

    /**
     * A method to get the state of the end of a Finger
     * @param finger The Finger that we want the information of
     * @return The state of the end of the Finger
     */
    public FingerState getTipState(Finger finger){
        return recognizeState(fingerCurveCalculator.fingerTip(finger));
    }

    /**
     * A method to get the state of the beginning of a Finger
     * @param finger The Finger that we want the information of
     * @return The state of the beginning of the Finger
     */
    public FingerState getStartState(Finger finger){
        return recognizeState(fingerCurveCalculator.fingerStart(finger));
    }

    /**
     * The method we use to know the state of a Finger from is curve
     * @param f The curve of the Finger
     * @return The State of the Finger
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
     * The getter of the FingerCurveCalculator we use in this class
     * @return The FingerCurveCalculator we use in this class
     */
    public FingerCurveCalculator getFingerCurveCalculator() {
        return fingerCurveCalculator;
    }
}
