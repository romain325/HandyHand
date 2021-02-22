package Core.Gesture.HandPoses.Poses;

import Core.Gesture.HandPoses.HandRecognizer;
import Core.Gesture.HandPoses.HandType;
import Core.Gesture.HandPoses.IGesture;
import com.leapmotion.leap.Frame;

import static Core.Gesture.HandPoses.HandType.getHand;

/**
 * A class to define the Rock gesture
 */
public class Rock implements IGesture {
    /**
     * A method to know if a hand is doing a Rock gesture in the frame
     * @param frame The Frame we want the information of
     * @param hand The type of the hand that we want the gesture
     * @return True if a hand is doing a Rock gesture in the frame, false otherwise
     */
    @Override
    public boolean invoke(Frame frame, HandType hand) {
        HandRecognizer handRecognizer = new HandRecognizer();
        return handRecognizer.isHandClosed(getHand(frame,hand));
    }
}
