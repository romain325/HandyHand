package Core.Gesture;

import Core.Gesture.HandPoses.HandType;
import Core.Gesture.HandPoses.IGesture;
import Core.Gesture.HandPoses.Poses.Fuck;
import Core.Gesture.HandPoses.Poses.Leaf;
import Core.Gesture.HandPoses.Poses.Rock;
import Core.Gesture.HandPoses.Poses.Scissors;
import com.leapmotion.leap.Frame;

import java.util.HashMap;
import java.util.Map;

public class GestureMatcher {
    private static final Map<String, IGesture> gestures = new HashMap<>();

    public static boolean gestureExist(String key){
        return gestures.containsKey(key);
    }

    public static void addGesture(String name, IGesture method) throws IllegalArgumentException {
        // TODO verification
        if(gestureExist(name)){
            throw new IllegalArgumentException("This Gesture Already Exists");
        }

        gestures.put(name,method);
    }

    public static boolean getResult(String key, Frame frame, HandType hand) throws IllegalArgumentException {
        if(!gestureExist(key)){
            throw new IllegalArgumentException("This gesture is not in the registry");
        }
        return gestures.get(key).invoke(frame, hand);
    }


    public static void init(){
        addGesture("rock", new Rock());
        addGesture("leaf", new Leaf());
        addGesture("scissors", new Scissors());
        addGesture("fuck", new Fuck());
    }
}
