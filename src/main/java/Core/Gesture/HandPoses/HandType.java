package Core.Gesture.HandPoses;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

/**
 * Define the type of a Hand
 */
public enum HandType {
    LEFT,
    RIGHT,
    BOTH
    ;

    /**
     * A method to get the Hand of the type we want from a frame
     * @param frame The frame from which we want the Hand
     * @param hand The type of the Hand we want
     * @return The Hand of the type in the frame, or null otherwise
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
