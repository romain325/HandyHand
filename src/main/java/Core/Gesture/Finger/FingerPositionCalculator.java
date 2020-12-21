package Core.Gesture.Finger;


import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Vector;

/**
 * A class to calculate different position of the finger
 */
public class FingerPositionCalculator {

    public boolean isFingersStick(Finger finger1, Finger finger2) {
        if(finger1 == null || !finger1.isValid() || finger2 == null || !finger2.isValid()) return false;

        return true;
    }

    /**
     * A function that says if two index have them intermediate bones sticks
     * @param finger1 The first index
     * @param finger2 The second index
     * @return Return true if both index have them intermediate bones sticks
     */
    public boolean isNextIntermediateIndexsSticks(Finger finger1, Finger finger2) {
        if(finger1 == null || !finger1.isValid() || finger2 == null || !finger2.isValid()) return false;

        Vector v1 = finger1.bone(Bone.Type.TYPE_INTERMEDIATE).nextJoint();
        Vector v2 = finger2.bone(Bone.Type.TYPE_INTERMEDIATE).nextJoint();

        int value; //The value of distance

        if(finger1.type() != Finger.Type.TYPE_INDEX) return false;
        if(finger2.type() != Finger.Type.TYPE_INDEX) return false;

//        System.out.println(v1.distanceTo(v2));

        //We found values by testing
        if(v1.distanceTo(v2) > 40) return false;
        return true;
    }

    /**
     * A function that says if 2 fingers are touching there tip
     * @param finger1 The first finger
     * @param finger2 The second finger
     * @return Return true if the bones are stick, false otherwise
     */
    public boolean isNextDistalSticks(Finger finger1, Finger finger2) {
        if(finger1 == null || !finger1.isValid() || finger2 == null || !finger2.isValid()) return false;

        Vector v1 = finger1.bone(Bone.Type.TYPE_DISTAL).nextJoint();
        Vector v2 = finger2.bone(Bone.Type.TYPE_DISTAL).nextJoint();

        Finger.Type type1 = finger1.type();
        Finger.Type type2 = finger2.type();

        int value; //The value of distance

        // The distance is different for each fingers
        //We found values by testing
        switch (type1) {
            case TYPE_THUMB:
                if(type2 == Finger.Type.TYPE_THUMB) value = 30;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 25;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 55;
                else if(type2 == Finger.Type.TYPE_RING) value = 60;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 35;
                else return false;
                break;
            case TYPE_INDEX:
                if(type2 == Finger.Type.TYPE_THUMB) value = 25;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 45;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 35;
                else if(type2 == Finger.Type.TYPE_RING) value = 25;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 35;
                else return false;
                break;
            case TYPE_MIDDLE:
                if(type2 == Finger.Type.TYPE_THUMB) value = 55;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 35;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 45;
                else if(type2 == Finger.Type.TYPE_RING) value = 45;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 35;
                else return false;
                break;
            case TYPE_RING:
                if(type2 == Finger.Type.TYPE_THUMB) value = 60;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 25;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 45;
                else if(type2 == Finger.Type.TYPE_RING) value = 50;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 40;
                else return false;
                break;
            case TYPE_PINKY:
                if(type2 == Finger.Type.TYPE_THUMB) value = 35;
                else if(type2 == Finger.Type.TYPE_INDEX) value = 35;
                else if(type2 == Finger.Type.TYPE_MIDDLE) value = 35;
                else if(type2 == Finger.Type.TYPE_RING) value = 40;
                else if(type2 == Finger.Type.TYPE_PINKY) value = 35;
                else return false;
                break;
            default:
                return false;
        }

//        System.out.println(v1.distanceTo(v2));

        if(v1.distanceTo(v2) > value) return false;
        return true;
    }

}
