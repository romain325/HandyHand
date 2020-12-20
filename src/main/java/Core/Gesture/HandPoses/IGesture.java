package Core.Gesture.HandPoses;

import com.leapmotion.leap.Frame;

public interface IGesture {
    boolean invoke(Frame frame, HandType hand);
}
