package Core.Gesture.HandPoses.Poses;

import Core.Gesture.HandPoses.HandRecognizer;
import Core.Gesture.HandPoses.HandType;
import Core.Gesture.HandPoses.IGesture;
import com.leapmotion.leap.Frame;

import static Core.Gesture.HandPoses.HandType.getHand;

public class Fuck implements IGesture {
    @Override
    public boolean invoke(Frame frame, HandType hand) {
        HandRecognizer handRecognizer = new HandRecognizer();
        return handRecognizer.isHandFuck(getHand(frame,hand));
    }
}