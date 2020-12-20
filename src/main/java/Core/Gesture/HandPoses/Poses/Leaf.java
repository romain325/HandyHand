package Core.Gesture.HandPoses.Poses;

import Core.Gesture.HandPoses.HandRecognizer;
import Core.Gesture.HandPoses.HandType;
import Core.Gesture.HandPoses.IGesture;
import com.leapmotion.leap.Frame;

import static Core.Gesture.HandPoses.HandType.getHand;

/**
 * A class to know if the hand is doing a leaf
 */
public class Leaf implements IGesture {
    @Override
    public boolean invoke(Frame frame, HandType hand) {
        HandRecognizer handRecognizer = new HandRecognizer();
        return handRecognizer.isHandOpen(getHand(frame,hand));
    }
}
