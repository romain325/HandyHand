package Core.Gesture;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;

import java.util.HashMap;
import java.util.Map;

public class FingerReconizer {

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type,Boolean> fingersBend(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Boolean> fingersBend = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersBend.put(finger.type(), FingerReconizer.isFingerBend(finger));
        }

        return fingersBend;
    }

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type,Boolean> fingersOut(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Boolean> fingersOut = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersOut.put(finger.type(), FingerReconizer.isFingerOut(finger));
        }

        return fingersOut;
    }

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type, Float> fingersCurve(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Float> fingersCurve = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersCurve.put(finger.type(), FingerReconizer.fingerCurve(finger));
        }

        return fingersCurve;
    }

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type,Boolean> fingersStartBend(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Boolean> fingersBend = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersBend.put(finger.type(), FingerReconizer.isFingerStartBend(finger));
        }

        return fingersBend;
    }

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type,Boolean> fingersStartOut(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Boolean> fingersOut = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersOut.put(finger.type(), FingerReconizer.isFingerStartOut(finger));
        }

        return fingersOut;
    }

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type, Float> fingersStartCurve(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Float> fingersCurve = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersCurve.put(finger.type(), FingerReconizer.fingerStartCurve(finger));
        }

        return fingersCurve;
    }

    /**
     *
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type,Boolean> fingertipsBend(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Boolean> fingersBend = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersBend.put(finger.type(), FingerReconizer.isFingertipBend(finger));
        }

        return fingersBend;
    }

    /**
     * A function that calculates the percentage of the curve of each fingers of the hand
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type,Boolean> fingertipsOut(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Boolean> fingersOut = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersOut.put(finger.type(), FingerReconizer.isFingertipOut(finger));
        }

        return fingersOut;
    }

    /**
     * A function that calculates the percentage of the curve of each fingers of the hand
     * @param hand A hand that we want the information of the fingers
     * @return
     */
    public static Map<Finger.Type, Float> fingertipsCurve(Hand hand) {
        if(hand == null) return null;
        if(!hand.isValid()) return null;

        Map<Finger.Type,Float> fingersCurve = new HashMap<>();

        for (Finger finger: hand.fingers()) {
            fingersCurve.put(finger.type(), FingerReconizer.fingertipCurve(finger));
        }

        return fingersCurve;
    }

    /**
     * A function that returns if the finger is bend
     * @param finger A finger that we want the information
     * @return A boolean who says if the finger is out
     */
    public static boolean isFingerOut(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        return isFingertipOut(finger) && isFingerStartOut(finger);
    }

    /**
     * A function that returns if the finger is out
     * @param finger A finger that we want the information
     * @return A boolean who says if the finger is bend
     */
    public static boolean isFingerBend(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        return isFingertipBend(finger) && isFingerStartBend(finger);
    }

    /**
     * A function that calculates the percentage of the curve of the finger
     * @param finger A finger that we want the information
     * @return The percentage of the curve of the finger
     */
    public static float fingerCurve(Finger finger) {
        if(finger == null) return -1;
        if(!finger.isValid() || !finger.isFinger()) return -1;

        float fingertipCurve = fingertipCurve(finger);
        float fingerStartCurve = fingerStartCurve(finger);

        //The average of both percentage
        return (fingertipCurve + fingerStartCurve) / 2;
    }

    /**
     * A function that returns if the beginning of the finger is bend
     * @param finger A finger that we want the information
     * @return A boolean who says if the beginning of the finger is bend
     */
    public static boolean isFingerStartBend(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        //Call the function to have the curve of the finger
        float curve = fingerStartCurve(finger);
        //System.out.println(finger.id() + " : " + curve);

        //If the curve is over 85%, the beginning of the finger is bend
        return curve > 85;
    }

    /**
     * A function that returns if the beginning of the finger is out
     * @param finger A finger that we want the information
     * @return A boolean who says if the beginning of the finger is out
     */
    public static boolean isFingerStartOut(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        //Call the function to have the curve of the finger
        float curve = fingerStartCurve(finger);

        //If the curve is below 15%, the beginning of the finger is out
        return curve < 15;
    }

    /**
     * A function that calculates the percentage of the curve of the beginning of the finger
     * @param finger A finger that we want the information
     * @return The percentage of the curve of the beginning of the finger.
     * The percentage for the thumb are restrict to 0%, 50% and 100% and may not be correct.
     * This is due to a probability for a great value that is low
     */
    public static float fingerStartCurve(Finger finger) {
        float percentage;
        if(finger == null) return -1;
        if(!finger.isValid() || !finger.isFinger()) return -1;

        //If the finger is not the thumb
        if(finger.type() != Finger.Type.TYPE_THUMB) {
            Bone meta = finger.bone(Bone.Type.TYPE_METACARPAL); //The metacarpal bone of the finger
            Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the finger

            Vector vMetaPrev = meta.prevJoint(); //The position of the beginning of metacarpal bone
            Vector vProxNext = prox.nextJoint(); //The position of the end of proximal bone

            float distance = vMetaPrev.distanceTo(vProxNext); //The distance between the two vector

            float lengthMeta = meta.length();
            float lengthProx = prox.length();
            float size = lengthMeta + lengthProx; //The size when bones aren't curve

            //Difference between the distance of the vector and the one when the finger isn't curve
            float interval = size-distance - 1; //Since there are imbalance, we use -1

            //Calculate the percentage
            percentage = interval*100/23; //Generally, when the finger is bend, the interval is ~23
            //Correction of the percentage
            percentage = percentage > 100 ? 100 : percentage;
            percentage = percentage < 1 ? 0 : percentage;
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
            if(distance<180) percentage = 50; //Values have been found by test, but may not be correct
            if(distance<125) percentage = 100; //Values have been found by test, but may not be correct
        }

        return percentage;
    }

    /**
     * A function that returns if the end of the finger is bend
     * @param finger A finger that we want the information
     * @return A boolean who says if the fingertip is bend
     */
    public static boolean isFingertipBend(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        //Call the function to have the curve of the finger
        float curve = fingertipCurve(finger);
        //System.out.println(finger.id() + " : " + curve);

        //If the curve is over 85%, the end of the finger is bend
        return curve > 85;
    }

    /**
     * A function that returns if the end of the finger is out
     * @param finger A finger that we want the information
     * @return A boolean who says if the fingertip is out
     */
    public static boolean isFingertipOut(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        //Call the function to have the curve of the finger
        float curve = fingertipCurve(finger);

        //If the curve is below 15%, the end of the finger is out
        return curve < 15;
    }

    /**
     * A function that calculates the percentage of the curve of the fingertip
     * @param finger The finger that we want the information
     * @return The percentage of the curve of the fingertip
     */
    public static float fingertipCurve(Finger finger) {
        if(finger == null) return -1;
        if(!finger.isValid() || !finger.isFinger()) return -1;


        Bone distal = finger.bone(Bone.Type.TYPE_DISTAL); //The distal bone of the finger
        Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL); //The proximal bone of the finger

        Vector vDistalNext = distal.nextJoint(); //The position of the end of distal bone
        Vector vProxPrev = prox.prevJoint(); //The position of the beginning of proximal bone

        float distance = vDistalNext.distanceTo(vProxPrev); //The distance between the two vector
        //float distance = (float) Math.sqrt(Math.pow((double)(vDistalNext.getX() - vProxPrev.getX()), 2.0) +
        //        Math.pow((double)(vDistalNext.getY() - vProxPrev.getY()), 2.0) +
        //        Math.pow((double)(vDistalNext.getZ() - vProxPrev.getZ()), 2.0));

        Bone inter = finger.bone(Bone.Type.TYPE_INTERMEDIATE); //The intermediate bone of the finger
        //The length of the fingertip
        float FingertipLenght = distal.length() + prox.length() + inter.length();

        float percentage = 0; //The percentage of the curve of the fingertip
        //Difference between the distance of the vector and the one when the finger isn't curve
        float interval = FingertipLenght-distance;


        //There are a difference between the thumb and others fingers
        switch (finger.type()) {
            case TYPE_THUMB -> {
                interval = interval < 1.5 ? 0 : FingertipLenght-distance;
                percentage = (interval)*100/25; //Generally, when the thumb is bend, the interval is ~25
                break;
            }
            case TYPE_INDEX -> {
                System.out.println("Index");
                System.out.println("vDistalNext : " + vDistalNext);
                System.out.println("vProxPrev : " + vProxPrev);
                System.out.println("FingertipLenght : " + FingertipLenght);
                System.out.println("distance : " + distance);
                System.out.println("interval : " + interval);
                percentage = (interval)*100/33; //Generally, when the finger is bend, the interval is ~34
                System.out.println("percentage : " + percentage);
                break;
            }
            case TYPE_MIDDLE -> {
                percentage = (interval)*100/34; //Generally, when the finger is bend, the interval is ~34
                break;
            }
            case TYPE_RING -> {
                percentage = (interval)*100/34; //Generally, when the finger is bend, the interval is ~34
                break;
            }
            case TYPE_PINKY -> {
                percentage = (interval)*100/34; //Generally, when the finger is bend, the interval is ~34
                break;
            }
        }

        //Correction of the percentage
        percentage = percentage > 100 ? 100 : percentage;
        percentage = percentage < 1 ? 0 : percentage;

        return percentage;
    }
}
