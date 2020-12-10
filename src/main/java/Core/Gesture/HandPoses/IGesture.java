package Core.Gesture.HandPoses;

import Core.Gesture.HandPoses.HandType;
import com.leapmotion.leap.Frame;

public interface IGesture {
    boolean invoke(Frame frame, HandType hand);
}
