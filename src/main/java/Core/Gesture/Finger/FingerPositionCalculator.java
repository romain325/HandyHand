package Core.Gesture.Finger;


import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Vector;

/**
 * A class to calculate different position of the finger
 */
public class FingerPositionCalculator {

    /**
     * A function that says if 2 fingers are touching there tip
     * @param finger1 The first finger
     * @param finger2 The second finger
     * @return Return true if the bones are stick, false otherwise
     */
    public boolean isBonesNextDistalSticks(Finger finger1, Finger finger2) {
        if(finger1 == null || !finger1.isValid() || finger2 == null || !finger2.isValid()) return false;

        Vector v1 = finger1.bone(Bone.Type.TYPE_DISTAL).nextJoint();
        Vector v2 = finger2.bone(Bone.Type.TYPE_DISTAL).nextJoint();

        Finger.Type type1 = finger1.type();
        Finger.Type type2 = finger2.type();

        System.out.println(v1.distanceTo(v2));

        int value; //The value of distance

        // The distance is different for each fingers
        //We found values by testing
        switch (type1) {
            case TYPE_THUMB:
                if(type2 == Finger.Type.TYPE_THUMB) value = 18;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 20;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 53;
                else if(type2 == Finger.Type.TYPE_RING) value = 55;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 32;
                else return false;
                break;
            case TYPE_INDEX:
                if(type2 == Finger.Type.TYPE_THUMB) value = 20;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 40;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 30;
                else if(type2 == Finger.Type.TYPE_RING) value = 20;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 30;
                else return false;
                break;
            case TYPE_MIDDLE:
                if(type2 == Finger.Type.TYPE_THUMB) value = 53;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 30;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 40;
                else if(type2 == Finger.Type.TYPE_RING) value = 40;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 30;
                else return false;
                break;
            case TYPE_RING:
                if(type2 == Finger.Type.TYPE_THUMB) value = 55;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 20;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 40;
                else if(type2 == Finger.Type.TYPE_RING) value = 45;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 35;
                else return false;
                break;
            case TYPE_PINKY:
                if(type2 == Finger.Type.TYPE_THUMB) value = 32;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 30;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 30;
                else if(type2 == Finger.Type.TYPE_RING) value = 35;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 30;
                else return false;
                break;
            default:
                return false;
        }

        if(v1.distanceTo(v2) > value) return false;
        return true;
    }

}
