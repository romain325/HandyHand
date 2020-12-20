package Core.Gesture.Finger;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

import javax.swing.text.MutableAttributeSet;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to calculate different curves of the finger
 */
public class FingerCurveCalculator {

    /**
     * Return the percentage of curve of the fingers of the hand
     * @param hand A hand that we want the information of the fingers
     * @return A Map with the percentage of curve of the fingers of the hand
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
     * Return the percentage of curve of the beginning of the fingers of the hand
     * @param hand A hand that we want the information of the fingers
     * @return A Map with the percentage of curve of the beginning of the fingers of the hand
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
     * Return the percentage of curve of the end of the fingers of the hand
     * @param hand A hand that we want the information of the fingers
     * @return A Map with the percentage of curve of the end of the fingers of the hand
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

        //The average of both percentage, start and end
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

        //The angle between the proximal and the metacarpal bones
        float angleProxMeta = angleProximalMetacarpal(finger);

        //The percentage of angle between the proximal and the metacarpal bones
        float percentProxMeta = 0;
        //The percentage of curve of the beginning of the finger
        float percentage = 0;

        //Values aren't the same for each fingers
        switch (finger.type()) {
            case TYPE_THUMB:
                Hand hand = finger.hand(); //The hand of the thumb
                Vector handCenter = hand.sphereCenter(); //The position of the center of the hand

                Bone proxThumb = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the thumb
                Vector vProxThumb = proxThumb.nextJoint(); //The position of the end of proximal bone

                //The distance between the bone and the center of the hand
                float distance = vProxThumb.distanceTo(handCenter);

                //Calculate the percentage
                //We found the value of distance for percentage by testing
                //When distance was above 172, the thumb is not curved
                percentage = 0;
                //When distance was between 135 and 172, the thumb is half curved
                if (distance < 172) percentage = 50;
                //When distance was under 135, the thumb is curved
                if (distance < 135) percentage = 100;
                break;
            case TYPE_INDEX:
                //For index
                //We found by test intervals of values when we bend the finger
                //angleProxMeta : between 97 and 177
                //We set the angle for starting at 0
                angleProxMeta = angleProxMeta - 97; //So between 0 and 80

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentProxMeta = angleProxMeta*100/80;

                break;
            case TYPE_MIDDLE:
                //For middle
                //We found by test intervals of values when we bend the finger
                //angleProxMeta : between 110 and 177
                //Middle and Ring fingers have the same values
            case TYPE_RING:
                //For ring
                //We found by test intervals of values when we bend the finger
                //angleProxMeta : between 110 and 177
                //We set the angle for starting at 0
                angleProxMeta = angleProxMeta - 110; //So between 0 and 67

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentProxMeta = angleProxMeta*100/67;

                break;
            case TYPE_PINKY:
                //For pinky
                //We found by test intervals of values when we bend the finger
                //angleProxMeta : between 115 and 177
                //We set the angle for starting at 0
                angleProxMeta = angleProxMeta - 115; //So between 0 and 62

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentProxMeta = angleProxMeta*100/62;

                break;
            default:
                return -1;
        }

        //For each fingers that isn't the thumb, we set the pourcentage to be at 100 when the finger is bend, and 0 when is out
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

        //The angle between the distal and the intermediate bones
        float angleDistalInter = angleDistalIntermediate(finger);
        //The angle between the intermediate and the proximal bones
        float angleInterProx = angleIntermediateProximal(finger);

        //The percentage of angle between the distal and the intermediate bones
        float percentDistalInter;
        //The percentage of angle between the intermediate and the proximal bones
        float percentInterProx;

        //Values aren't the same for each fingers
        switch (finger.type()) {
            case TYPE_THUMB:
                //For thumb
                //We found by test intervals of values when we bend the finger
                //angleDistalInter : between 103 and 153
                //angleInterProx : between 137 and 174
                //We set angles for starting at 0
                angleDistalInter = angleDistalInter - 103; //So between 0 and 50
                angleInterProx = angleInterProx - 137; //So between 0 and 37

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentDistalInter = angleDistalInter*100/50;
                percentInterProx = angleInterProx*100/37;

                break;
            case TYPE_INDEX:
                //For index
                //We found by test intervals of values when we bend the finger
                //angleDistalInter : between 110 and 177
                //angleInterProx : between 100 and 175
                //We set angles for starting at 0
                angleDistalInter = angleDistalInter - 110; //So between 0 and 67
                angleInterProx = angleInterProx - 100; //So between 0 and 75

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentDistalInter = angleDistalInter*100/67;
                percentInterProx = angleInterProx*100/75;

                break;
            case TYPE_MIDDLE:
                //For middle
                //We found by test intervals of values when we bend the finger
                //angleDistalInter : between 142 and 176
                //angleInterProx : between 95 and 178
                //We set angles for starting at 0
                angleDistalInter = angleDistalInter - 142; //So between 0 and 34
                angleInterProx = angleInterProx - 95; //So between 0 and 83

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentDistalInter = angleDistalInter*100/34;
                percentInterProx = angleInterProx*100/83;

                break;
            case TYPE_RING:
                //For ring
                //We found by test intervals of values when we bend the finger
                //angleDistalInter : between 140 and 177
                //angleInterProx : between 94 and 177
                //We set angles for starting at 0
                angleDistalInter = angleDistalInter - 140; //So between 0 and 37
                angleInterProx = angleInterProx - 94; //So between 0 and 83

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentDistalInter = angleDistalInter*100/37;
                percentInterProx = angleInterProx*100/83;

                break;
            case TYPE_PINKY:
                //For pinky
                //We found by test intervals of values when we bend the finger
                //angleDistalInter : between 131 and 177
                //angleInterProx : between 107 and 177
                //We set angles for starting at 0
                angleDistalInter = angleDistalInter - 131; //So between 0 and 46
                angleInterProx = angleInterProx - 107; //So between 0 and 70

                //Now we calculate the percentage with the maximum value of angle by cross-multiplication
                percentDistalInter = angleDistalInter*100/46;
                percentInterProx = angleInterProx*100/70;

                break;
            default:
                return -1;
        }

        //Calculate the average of both percentages
        float percentage = (percentDistalInter + percentInterProx) / 2;

        //We set the pourcentage to be at 100 when the finger is bend, and 0 when is out
        percentage = Math.abs(100 - percentage);

        //Correction of the percentage
        percentage = percentage > 100 ? 100 : percentage;
        percentage = percentage < 1 ? 0 : percentage;

        return percentage;
    }

    /**
     * Calculate the angle between both bones proximal and metacarpale
     * @param finger The finger that we want the angle
     * @return The angle between both bones proximal and metacarpale
     */
    public float angleProximalMetacarpal(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;
        if(finger.type() == Finger.Type.TYPE_THUMB) return -1;

        return angle2Bones(finger.bone(Bone.Type.TYPE_INTERMEDIATE), finger.bone(Bone.Type.TYPE_PROXIMAL));
    }

    /**
     * Calculate the angle between both bones intermediate and proximal
     * @param finger The finger that we want the angle
     * @return The angle between both bones intermediate and proximal
     */
    public float angleIntermediateProximal(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        return angle2Bones(finger.bone(Bone.Type.TYPE_INTERMEDIATE), finger.bone(Bone.Type.TYPE_PROXIMAL));
    }

    /**
     * Calculate the angle between both bones distal and intermediate
     * @param finger The finger that we want the angle
     * @return The angle between both bones distal and intermediate
     */
    public float angleDistalIntermediate(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        return angle2Bones(finger.bone(Bone.Type.TYPE_DISTAL), finger.bone(Bone.Type.TYPE_INTERMEDIATE));
    }

    /**
     * Calcule the angle between two bones
     * For mor details : https://openclassrooms.com/forum/sujet/mathsespace-3d-calculer-l-angle-entre-2-vecteurs-87922
     * @param bone1 The first bone, have to be the one whose away of the hand
     * @param bone2 The second bone, have to be the one whose the closest of the hand
     * @return The angle between the two bones
     */
    public float angle2Bones(Bone bone1, Bone bone2) {
        if(bone1 == null || !bone1.isValid() || bone2 == null || !bone2.isValid()) return -1;
        if(bone1.length() == 0 || bone2.length() == 0) return -1;

        if(bone2.type() == Bone.Type.TYPE_METACARPAL) {
            if (bone2.type() != Bone.Type.TYPE_PROXIMAL) return -1;
        }
        else if(bone2.type() == Bone.Type.TYPE_PROXIMAL) {
            if (bone2.type() != Bone.Type.TYPE_INTERMEDIATE) return -1;
        }
        else if(bone2.type() == Bone.Type.TYPE_INTERMEDIATE) {
            if (bone2.type() != Bone.Type.TYPE_DISTAL) return -1;
        } else {
            return -1;
        }

        //We take the three vector, for next first bone, the previous for second bone, and the one between the both
        Vector vB = bone1.nextJoint(); //B
        Vector vA = bone1.prevJoint(); //A
        Vector vC = bone2.prevJoint(); //C

        //We calculate the vector of AB
        Vector vAB = new Vector(vB.getX() - vA.getX(),
                vB.getY() - vA.getY(),
                vB.getZ() - vA.getZ()); //AB

        //We calculate the vector of AC
        Vector vAC = new Vector(vC.getX() - vA.getX(),
                vC.getY() - vA.getY(),
                vC.getZ() - vA.getZ()); //AC

        //We calculate the absolute of the vector AB
        float absAB = (float) Math.sqrt(
                Math.pow(vAB.getX(), 2) +
                        Math.pow(vAB.getY(), 2) +
                        Math.pow(vAB.getZ(), 2)); // ||AB||

        //We calculate the absolute of the vector AC
        float absAC = (float) Math.sqrt(
                Math.pow(vAC.getX(), 2) +
                        Math.pow(vAC.getY(), 2) +
                        Math.pow(vAC.getZ(), 2)); // ||AC||

        //We calculate the multiplication of the vector AB and AC
        float ABAC = vAB.getX() * vAC.getX() +
                vAB.getY() * vAC.getY() +
                vAB.getZ() * vAC.getZ(); //AB.AC

        //We calculate the angle radiant
        float radianAngle = (float) Math.acos(ABAC / (absAB * absAC)); //Angle A

        //We calculate the degres of angle : 1 radian = 57.2958 degrés
        return (float) (radianAngle * 57.2958);
    }

    /**
     * Calculate the percentage to know if the thumb is stick to the index
     * @param finger The thumb we want to know informations
     * @return 100 if the thumb is stick to the index, 0 if he's at the opposite, and 50 if is between stick and at the opposite
     */
    public float thumbStickingIndex(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()
                || finger.type() != Finger.Type.TYPE_THUMB) return -1;

        //The metacarpal bone of the index finger of the hand of the thumb
        Bone indexMeta = finger.hand().fingers().fingerType(Finger.Type.TYPE_INDEX).get(0).bone(Bone.Type.TYPE_METACARPAL);

        //The position of the center of the metacarpal bone of the index
        Vector indexMetaCenter = indexMeta.center();

        Bone proxThumb = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the thumb
        Vector vProxThumb = proxThumb.nextJoint(); //The position of the end of proximal bone

        //The distance between the bone and the center of the metacarpal bone of the index
        float distance = vProxThumb.distanceTo(indexMetaCenter);

        //Calculate the percentage
        //We found the value of distance for percentage by testing
        //When distance was between 30 and 38, the thumb is half stick to the index
        float percentage = 50;
        //When distance was under 30, the thumb is curved
        if (distance < 30) percentage = 100;
        //When distance was above 38, the thumb is not stick to the index
        if (distance > 38) percentage = 0;
        return 0;
    }
}





