package Core.Gesture.Finger;

import Core.Gesture.Finger.FingerCurveCalculator;
import Core.Gesture.Finger.FingerState;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;

import java.util.HashMap;
import java.util.Map;

public class FingerStateRecognizer {
    private final FingerCurveCalculator fingerCurveCalculator = new FingerCurveCalculator();

    public Map<Finger.Type, FingerState> getFingersState(Hand hand){
        Map<Finger.Type,FingerState> fingerStateMap = new HashMap<>();
        for (Finger finger: hand.fingers()) {
            fingerStateMap.put(finger.type(), getFingerState(finger));
        }
        return fingerStateMap;
    }

    public FingerState getFingerState(Finger finger){
        FingerState state = getTipState(finger);
        if(state == getStartState(finger)){
            return state;
        }else{
            return FingerState.NORMAL;
        }
    }

    public FingerState getTipState(Finger finger){
        return recognizeState(fingerCurveCalculator.fingerTip(finger));
    }

    public FingerState getStartState(Finger finger){
        return recognizeState(fingerCurveCalculator.fingerStart(finger));
    }

    private FingerState recognizeState(float f){
        if(f < 0){
            return FingerState.ERROR;
        }
        if(f < 15){
            return FingerState.OUT;
        }
        if (f > 75) {
            return FingerState.BENDING;
        }
        return FingerState.NORMAL;
    }

    public FingerCurveCalculator getFingerCurveCalculator() {
        return fingerCurveCalculator;
    }
}
