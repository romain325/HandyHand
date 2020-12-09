package Core.Gesture;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class GestureMatcher {
    private final Map<String, Method> gestures = new HashMap<>();

    public boolean gestureExist(String key){
        return gestures.containsKey(key);
    }

    public void addGesture(String name, Method method) throws IllegalArgumentException {
        // TODO verification
        if(gestureExist(name)){
            throw new IllegalArgumentException("This Gesture Already Exists");
        }

        gestures.put(name,method);
    }
}
