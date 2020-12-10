package Core.Gesture.Finger;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

import javax.swing.text.MutableAttributeSet;
import java.util.HashMap;
import java.util.Map;

public class FingerCurveCalculator {

    /**
     * @param hand A hand that we want the information of the fingers
     * @return
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
     * @param hand A hand that we want the information of the fingers
     * @return
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
     * A function that calculates the percentage of the curve of each fingers of the hand
     *
     * @param hand A hand that we want the information of the fingers
     * @return
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
     *
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
     *
     * @param finger A finger that we want the information
     * @return The percentage of the curve of the beginning of the finger.
     * The percentage for the thumb are restrict to 0%, 50% and 100% and may not be correct.
     * This is due to a probability for a great value that is low
     */
    public float fingerStart(Finger finger) {
        float percentage = -1;
        if (finger == null) return -1;
        if (!finger.isValid() || !finger.isFinger()) return -1;

        //If the finger is not the thumb
        if (finger.type() != Finger.Type.TYPE_THUMB) {
            Bone meta = finger.bone(Bone.Type.TYPE_METACARPAL); //The metacarpal bone of the finger
            Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the finger

            Vector vMetaPrev = meta.prevJoint(); //The position of the beginning of metacarpal bone
            Vector vProxNext = prox.nextJoint(); //The position of the end of proximal bone

            float distance = vMetaPrev.distanceTo(vProxNext); //The distance between the two vector

            float lengthMeta = meta.length();
            float lengthProx = prox.length();
            float size = lengthMeta + lengthProx; //The size when bones aren't curve

            //Difference between the distance of the vector and the one when the finger isn't curve
            float interval = size - distance - 1; //Since there are imbalance, we use -1

            //Calculate the percentage
            switch(finger.type()) {
                case TYPE_INDEX:
                    percentage = interval * 100 / 21; //Generally, when the index is bend, the interval is ~21
                    break;
                case TYPE_MIDDLE:
                    percentage = interval * 100 / 23; //Generally, when the finger is bend, the interval is ~23
                    break;
                case TYPE_RING:
                    percentage = interval * 100 / 23; //Generally, when the finger is bend, the interval is ~23
                    break;
                case TYPE_PINKY:
                    break;
            }

            percentage = Math.round(percentage/10)*10;

            //Correction of the percentage
            percentage = percentage > 100 ? 100 : percentage;
            percentage = percentage < 1 ? 0 : percentage;

            if (finger.type() == Finger.Type.TYPE_INDEX) {
                System.out.println("\nIndex");
                System.out.println("vMetaPrev : " + vMetaPrev);
                System.out.println("vProxNext : " + vProxNext);
                System.out.println("size : " + size);
                System.out.println("distance : " + distance);
                //System.out.println("interval : " + interval);
                System.out.println("percentage : " + percentage);
            }
        }
        //If the finger is the thumb
        else {
            Hand hand = finger.hand(); //The hand of the thumb
            Vector handCenter = hand.sphereCenter(); //The position of the center of the hand

            Bone proxThumb = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the thumb
            Vector vProxThumb = proxThumb.nextJoint(); //The position of the end of proximal bone

            float distance = vProxThumb.distanceTo(handCenter); //The distance between the bone and the center of the hand

            //Calculate the percentage
            percentage = 0;
            if (distance < 180) percentage = 50; //Values have been found by test, but may not be correct
            if (distance < 125) percentage = 100; //Values have been found by test, but may not be correct
        }

        return percentage;
    }

    /**
     * A function that calculates the percentage of the curve of the fingertip
     *
     * @param finger The finger that we want the information
     * @return The percentage of the curve of the fingertip
     */
    public float fingerTip(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;


        Bone distal = finger.bone(Bone.Type.TYPE_DISTAL); //The distal bone of the finger
        Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the finger

        Vector vDistalNext = distal.nextJoint(); //The position of the end of distal bone
        Vector vProxPrev = prox.prevJoint(); //The position of the beginning of proximal bone

        //float distance = vDistalNext.distanceTo(vProxPrev); //The distance between the two vector
        float distance = (float) Math.sqrt(Math.pow((double)(vDistalNext.getX() - vProxPrev.getX()), 2.0) +
                Math.pow((double)(vDistalNext.getY() - vProxPrev.getY()), 2.0));

        Bone inter = finger.bone(Bone.Type.TYPE_INTERMEDIATE); //The intermediate bone of the finger
        //The length of the fingertip
        float FingertipLenght = distal.length() + prox.length() + inter.length();

        float percentage = 0; //The percentage of the curve of the fingertip
        //Difference between the distance of the vector and the one when the finger isn't curve

        float interval = FingertipLenght-distance;

        //There are a difference between the thumb and others fingers
        switch (finger.type()) {
            case TYPE_THUMB:
                interval = interval < 1.5 ? 0 : FingertipLenght-distance;
                percentage = (interval)*100/25; //Generally, when the thumb is bend, the interval is ~25
                break;

            case TYPE_INDEX:
                percentage = (interval)*100/33; //Generally, when the finger is bend, the interval is ~34


                System.out.println("\nIndex");
                System.out.println("vDistalNext : " + vDistalNext);
                System.out.println("vProxPrev : " + vProxPrev);
                System.out.println("FingertipLenght : " + FingertipLenght);
                System.out.println("distance : " + distance);
                //System.out.println("interval : " + interval);
                //System.out.println("percentage : " + percentage);
                break;

            case TYPE_MIDDLE:
            case TYPE_RING:
            case TYPE_PINKY:
                percentage = (interval)*100/34; //Generally, when the finger is bend, the interval is ~34
                break;
        }

        //Correction of the percentage
        percentage = percentage > 100 ? 100 : percentage;
        percentage = percentage < 1 ? 0 : percentage;

        return percentage;
    }

    public float fingerTipCurve(Finger finger) {
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

    public float angleProximalMetacarpal(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;
        if(finger.type() == Finger.Type.TYPE_THUMB) return -1;

        float degresAngle = angle2Bones(finger.bone(Bone.Type.TYPE_INTERMEDIATE), finger.bone(Bone.Type.TYPE_PROXIMAL));

        return degresAngle;
    }

    public float angleIntermediateProximal(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        float degresAngle = angle2Bones(finger.bone(Bone.Type.TYPE_INTERMEDIATE), finger.bone(Bone.Type.TYPE_PROXIMAL));

        return degresAngle;
    }

    public float angleDistalIntermediate(Finger finger) {
        if(finger == null || !finger.isValid() || !finger.isFinger()) return -1;

        float degresAngle = angle2Bones(finger.bone(Bone.Type.TYPE_DISTAL), finger.bone(Bone.Type.TYPE_INTERMEDIATE));

        return degresAngle;
    }

    /**
     * Pour plus d'explications : https://openclassrooms.com/forum/sujet/mathsespace-3d-calculer-l-angle-entre-2-vecteurs-87922
     * @param bone1
     * @param bone2
     * @return
     */
    public float angle2Bones(Bone bone1, Bone bone2) {
        if(bone1 == null || !bone1.isValid() || bone2 == null || !bone2.isValid()) return -1;
        if(bone1.length() == 0 || bone2.length() == 0) return -1;

        //TODO : Vérifier que les os soient collés et que le bone1 soit plus éloigné de la main que le bone2
        //(Genre bone1 : Distal et bone2 : Intermediate, mais pas l'inverse)

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
    }
}
