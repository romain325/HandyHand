package Core.Gesture.HandPoses;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

public enum HandType {
    LEFT,
    RIGHT,
    BOTH
    ;

    public static Hand getHand(Frame frame, HandType hand){
        for (Hand h : frame.hands()) {
            if(hand == HandType.BOTH && h.isValid()){
                return h;
            }
            if(hand == HandType.LEFT && h.isValid() && h.isLeft()){
                return h;
            }
            if(hand == HandType.RIGHT && h.isValid() && h.isRight()){
                return h;
            }
        }
        return null;
    }
}
