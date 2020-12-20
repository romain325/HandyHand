package Core.Gesture.HandPoses;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

/**
 * A class to know the type of the hand
 */
public enum HandType {
    LEFT,
    RIGHT,
    BOTH
    ;

    /**
     * A method to get the hand by the hand type
     * @param frame The frame from where we want the hand
     * @param hand The type of the hand that we want
     * @return The hand of type we want, of null if there's not
     */
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
