package Core.Gesture.HandPoses;

import com.leapmotion.leap.Frame;

/**
 * An interface to define a method to call for gestures
 */
public interface IGesture {
    /**
     * A method to know if the Gesture is done in the frame
     * @param frame The Frame we want the information of
     * @param hand The type of the hand that we want the gesture
     * @return True if the gesture is done, false otherwise
     */
    boolean invoke(Frame frame, HandType hand);
}
