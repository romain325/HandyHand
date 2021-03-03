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

/**
 * A class to manage some match of gestures
 */
public class GestureMatcher {
    /**
     * All the gestures that exist
     */
    private static final Map<String, IGesture> gestures = new HashMap<>();

    /**
     * A method to know if the gesture are implemented
     * @param key The name of the gesture we want the information of
     * @return True if the gesture are implemented, false otherwise
     */
    public static boolean gestureExist(String key){
        return gestures.containsKey(key);
    }

    /**
     * A method to implement a new gesture
     * @param name The name of the gesture
     * @param method The class where the gesture is code
     * @throws IllegalArgumentException If the gesture already exist
     */
    public static void addGesture(String name, IGesture method) throws IllegalArgumentException {
        // TODO verification
        if(gestureExist(name)){
            throw new IllegalArgumentException("This Gesture Already Exists");
        }

        gestures.put(name,method);
    }

    /**
     * A method to know if a gesture are being done in a Frame
     * @param key The name of the gesture we want the information of
     * @param frame The frame we want the information of
     * @param hand The type of the hand we want the information of
     * @return True if the gesture is being done, false otherwise
     * @throws IllegalArgumentException If the gesture aren't registered
     */
    public static boolean getResult(String key, Frame frame, HandType hand) throws IllegalArgumentException {
        if(!gestureExist(key)){
            throw new IllegalArgumentException("This gesture is not in the registry");
        }
        return gestures.get(key).invoke(frame, hand);
    }

    /**
     * A method to init all the gestures that are implemented
     */
    public static void init(){
        addGesture("rock", new Rock());
        addGesture("leaf", new Leaf());
        addGesture("scissors", new Scissors());
        addGesture("fuck", new Fuck());
    }
}
