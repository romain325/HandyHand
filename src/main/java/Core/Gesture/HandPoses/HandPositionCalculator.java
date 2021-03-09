package Core.Gesture.HandPoses;

import com.leapmotion.leap.*;

/**
 * A class to calculate different position of the hand
 */
public class HandPositionCalculator {

    /**
     * A method to know if 2 hands are stick by the pinky finger
     * @param hands The handList that contains both fingers
     * @return Return true if both hands are sitck, else otherwise
     */
    public boolean isHandsStick(HandList hands) {
        if(hands.count() != 2) return false;
        Hand hand1 = hands.get(0);
        Hand hand2 = hands.get(1);
        if(hand1 == null || !hand1.isValid() || hand2 == null || !hand2.isValid()) return false;

        Vector vector1 = hand1.fingers().fingerType(Finger.Type.TYPE_PINKY).get(0).bone(Bone.Type.TYPE_METACARPAL).nextJoint();
        Vector vector2 = hand2.fingers().fingerType(Finger.Type.TYPE_PINKY).get(0).bone(Bone.Type.TYPE_METACARPAL).nextJoint();

        if(vector1.distanceTo(vector2) > 35) return false;

        return true;
    }
}
