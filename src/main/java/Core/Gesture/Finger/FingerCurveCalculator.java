package Core.Gesture.Finger;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

import javax.swing.text.MutableAttributeSet;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to calculate some curve of fingers
 */
public class FingerCurveCalculator {

    /**
     * A method to get the curves of each fingers of a Hand
     * @param hand The hand that we want the information of the fingers
     * @return A Map with the curves of each fingers with there type
     */
    public Map<Finger.Type, Float> fingersCurve(Hand hand) {
        if (hand == null || !hand.isValid()) return null;

        Map<Finger.Type, Float> fingersCurve = new HashMap<>();

        for (Finger finger : hand.fingers()) {
            fingersCurve.put(finger.type(), fingerCurve(finger));
        }

        return fingersCurve;
    }

    /**
     * A method to get the curves of the beginning of each fingers of a Hand
     * @param hand A hand that we want the information of the fingers
     * @return A Map with the curves of the beginning of each fingers with there type
     */
    public Map<Finger.Type, Float> fingersStart(Hand hand) {
        if (hand == null || !hand.isValid()) return null;

        Map<Finger.Type, Float> fingersCurve = new HashMap<>();

        for (Finger finger : hand.fingers()) {
            fingersCurve.put(finger.type(), fingerStart(finger));
        }

        return fingersCurve;
    }

    /**
     * A function that calculates the percentage of the curve of the end of each fingers of the hand
     * @param hand A hand that we want the information of the fingers
     * @return A Map with the curves of the end of each fingers with there type
     */
    public Map<Finger.Type, Float> fingersTips(Hand hand) {
        if (hand == null || !hand.isValid()) return null;

        Map<Finger.Type, Float> fingersCurve = new HashMap<>();

        for (Finger finger : hand.fingers()) {
            fingersCurve.put(finger.type(), fingerTip(finger));
        }

        return fingersCurve;
    }


    /**
     * A function that calculates the percentage of the curve of the finger
     * @param finger A finger that we want the information
     * @return The percentage of the curve of the finger
     */
    public float fingerCurve(Finger finger) {
        if (finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        //The average of both percentage
        return (fingerTip(finger) + fingerStart(finger)) / 2;
    }

    /**
     * A function that calculates the percentage of the curve of the beginning of the finger
     * @param finger A finger that we want the information
     * @return The percentage of the curve of the beginning of the finger.
     * The percentage for the thumb are restrict to 0%, 50% and 100% and may not be correct.
     * This is due to a probability for a great value that is low
     */
    public float fingerStart(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        float angleProxMeta = angleProximalMetacarpal(finger);

        float percentProxMeta = 0;
        float percentage = 0;

        switch (finger.type()) {
            case TYPE_THUMB:
                Hand hand = finger.hand(); //The hand of the thumb
                Vector handCenter = hand.sphereCenter(); //The position of the center of the hand

                Bone proxThumb = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the thumb
                Vector vProxThumb = proxThumb.nextJoint(); //The position of the end of proximal bone

                float distance = vProxThumb.distanceTo(handCenter); //The distance between the bone and the center of the hand

                //Calculate the percentage
                percentage = 0;
                if (distance < 172) percentage = 50; //Values have been found by test, but may not be correct
                if (distance < 135) percentage = 100; //Values have been found by test, but may not be correct
                break;
            case TYPE_INDEX:
                //For index
                //angleProxMeta : between 97 and 177
                angleProxMeta = angleProxMeta - 97; //So between 0 and 80

                percentProxMeta = angleProxMeta*100/80;

                break;
            case TYPE_MIDDLE:
                //For middle
                //angleProxMeta : between 110 and 177
                angleProxMeta = angleProxMeta - 110; //So between 0 and 67

                percentProxMeta = angleProxMeta*100/67;

                break;
            case TYPE_RING:
                //For ring
                //angleProxMeta : between 110 and 177
                angleProxMeta = angleProxMeta - 110; //So between 0 and 67

                percentProxMeta = angleProxMeta*100/67;

                break;
            case TYPE_PINKY:
                //For pinky
                //angleProxMeta : between 115 and 177
                angleProxMeta = angleProxMeta - 115; //So between 0 and 62

                percentProxMeta = angleProxMeta*100/62;

                break;
            default:
                return -1;
        }

        if(finger.type() != Finger.Type.TYPE_THUMB) percentage = Math.abs(100 - percentProxMeta);

        //Correction of the percentage
        percentage = percentage > 100 ? 100 : percentage;
        percentage = percentage < 1 ? 0 : percentage;

        return percentage;
    }

    /**
     * A function that calculates the percentage of the curve of the fingertip
     * @param finger The finger that we want the information
     * @return The percentage of the curve of the fingertip
     */
    public float fingerTip(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        float angleDistalInter = angleDistalIntermediate(finger);
        float angleInterProx = angleIntermediateProximal(finger);

        float percentDistalInter;
        float percentInterProx;

        switch (finger.type()) {
            case TYPE_THUMB:
                //For thumb
                //angleDistalInter : between 103 and 153
                //angleInterProx : between 137 and 174
                angleDistalInter = angleDistalInter - 103; //So between 0 and 50
                angleInterProx = angleInterProx - 137; //So between 0 and 37

                percentDistalInter = angleDistalInter*100/50;
                percentInterProx = angleInterProx*100/37;

                break;
            case TYPE_INDEX:
                //For index
                //angleDistalInter : between 110 and 177
                //angleInterProx : between 100 and 175
                angleDistalInter = angleDistalInter - 110; //So between 0 and 67
                angleInterProx = angleInterProx - 100; //So between 0 and 75

                percentDistalInter = angleDistalInter*100/67;
                percentInterProx = angleInterProx*100/75;

                break;
            case TYPE_MIDDLE:
                //For middle
                //angleDistalInter : between 142 and 176
                //angleInterProx : between 95 and 178
                angleDistalInter = angleDistalInter - 142; //So between 0 and 34
                angleInterProx = angleInterProx - 95; //So between 0 and 83

                percentDistalInter = angleDistalInter*100/34;
                percentInterProx = angleInterProx*100/83;

                break;
            case TYPE_RING:
                //For ring
                //angleDistalInter : between 140 and 177
                //angleInterProx : between 94 and 177
                angleDistalInter = angleDistalInter - 140; //So between 0 and 37
                angleInterProx = angleInterProx - 94; //So between 0 and 83

                percentDistalInter = angleDistalInter*100/37;
                percentInterProx = angleInterProx*100/83;

                break;
            case TYPE_PINKY:
                //For pinky
                //angleDistalInter : between 131 and 177
                //angleInterProx : between 107 and 177
                angleDistalInter = angleDistalInter - 131; //So between 0 and 46
                angleInterProx = angleInterProx - 107; //So between 0 and 70

                percentDistalInter = angleDistalInter*100/46;
                percentInterProx = angleInterProx*100/70;

                break;
            default:
                return -1;
        }

        float percentage = (percentDistalInter + percentInterProx) / 2;

        percentage = Math.abs(100 - percentage);

        //Correction of the percentage
        percentage = percentage > 100 ? 100 : percentage;
        percentage = percentage < 1 ? 0 : percentage;

        return percentage;
    }

    /**
     * A method to calculate the angle between the proximal and metacarpal bone of a Finger
     * @param finger The Finger from which we want to calculate
     * @return The angle between the proximal and metacarpal bone of a Finger
     */
    public float angleProximalMetacarpal(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;
        if(finger.type() == Finger.Type.TYPE_THUMB) return -1;

        float degresAngle = angle2Bones(finger.bone(Bone.Type.TYPE_PROXIMAL), finger.bone(Bone.Type.TYPE_METACARPAL));

        return degresAngle;
    }

    /**
     * A method to calculate the angle between the intermediate and proximal bone of a Finger
     * @param finger The Finger from which we want to calculate
     * @return The angle between the proximal and metacarpal bone of a Finger
     */
    public float angleIntermediateProximal(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        float degresAngle = angle2Bones(finger.bone(Bone.Type.TYPE_INTERMEDIATE), finger.bone(Bone.Type.TYPE_PROXIMAL));

        return degresAngle;
    }

    /**
     * A method to calculate the angle between the distal and intermediate bone of a Finger
     * @param finger The Finger from which we want to calculate
     * @return The angle between the proximal and metacarpal bone of a Finger
     */
    public float angleDistalIntermediate(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        float degresAngle = angle2Bones(finger.bone(Bone.Type.TYPE_DISTAL), finger.bone(Bone.Type.TYPE_INTERMEDIATE));

        return degresAngle;
    }

    /**
     * A method to calculate the angle between two bones
     * For more details : https://openclassrooms.com/forum/sujet/mathsespace-3d-calculer-l-angle-entre-2-vecteurs-87922
     * @param bone1 The first bone that we want to calculate
     * @param bone2 The second bone that we want to calculate
     * @return The angle between two bones
     */
    public float angle2Bones(Bone bone1, Bone bone2) {
        if(bone1 == null || !bone1.isValid() || bone2 == null || !bone2.isValid()) return -1;
        if(bone1.length() == 0 || bone2.length() == 0) return -1;

        //Verify if both bones are stick
        if(bone1.type() == Bone.Type.TYPE_DISTAL && bone2.type() == Bone.Type.TYPE_INTERMEDIATE ||
                bone1.type() == Bone.Type.TYPE_INTERMEDIATE && bone2.type() == Bone.Type.TYPE_PROXIMAL ||
                bone1.type() == Bone.Type.TYPE_PROXIMAL && bone2.type() == Bone.Type.TYPE_METACARPAL)
        {
            Vector vB = bone1.nextJoint(); //B
            Vector vA = bone1.prevJoint(); //A
            Vector vC = bone2.prevJoint(); //C

            Vector vAB = new Vector(vB.getX() - vA.getX(),
                    vB.getY() - vA.getY(),
                    vB.getZ() - vA.getZ()); //AB

            Vector vAC = new Vector(vC.getX() - vA.getX(),
                    vC.getY() - vA.getY(),
                    vC.getZ() - vA.getZ()); //AC

            float absAB = (float) Math.sqrt(
                    Math.pow(vAB.getX(), 2) +
                            Math.pow(vAB.getY(), 2) +
                            Math.pow(vAB.getZ(), 2)); // ||AB||

            float absAC = (float) Math.sqrt(
                    Math.pow(vAC.getX(), 2) +
                            Math.pow(vAC.getY(), 2) +
                            Math.pow(vAC.getZ(), 2)); // ||AC||

            float ABAC = vAB.getX() * vAC.getX() +
                    vAB.getY() * vAC.getY() +
                    vAB.getZ() * vAC.getZ(); //AB.AC

            float radianAngle = (float) Math.acos(ABAC / (absAB * absAC)); //Angle A

            float degresAngle = (float) (radianAngle * 57.2958); //1 radian = 57.2958 degrés

            return degresAngle;
        } else return 0;
    }
}
