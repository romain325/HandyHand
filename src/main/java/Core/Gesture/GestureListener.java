
package Core.Gesture;

import com.leapmotion.leap.*;

import java.util.Map;

public class GestureListener extends Listener {

    public void onConnect(Controller controller) {
        //controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        if(frame.id() % 120 != 0 || !frame.isValid()) return;
        //METHODE A APPELER
        frameInfo(frame);
    }

    public void frameInfo(Frame frame) {
        if(!frame.isValid()) {
            System.out.println("Frame not valid");
            return;
        };
        //Appelle les différentes méthodes d'analyse des frames..
        //handsInfo(frame.hands());
        System.out.println();
        //FingerReconizerTest(frame.hands().get(0));
        //HandReconizerTest(frame.hands().get(0));
        fingertipTest(frame.hands().get(0));
    }

    public void fingertipTest(Hand hand) {
        if(hand == null) {
            System.out.println("Hand null");
            return;
        }
        if(!hand.isValid()) {
            System.out.println("Hand not valid");
            return;
        }

        System.out.println("fingertipsCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerReconizer.fingertipsCurve(hand).entrySet()) {
            //System.out.println(e.getKey() + " : " + e.getValue());
        }
    }

    public void HandReconizerTest(Hand hand) {
        if(hand == null) {
            System.out.println("Hand null");
            return;
        }
        if(!hand.isValid()) {
            System.out.println("Hand not valid");
            return;
        }

        System.out.println("\n");

        System.out.println("isHandClosed : " + HandReconizer.isHandClosed(hand));
        System.out.println("isHandOpen : " + HandReconizer.isHandOpen(hand));
        System.out.println("isHandScissors : " + HandReconizer.isHandScissors(hand));
        System.out.println("countHandFingers : " + HandReconizer.countHandFingersOut(hand));

        System.out.println("\n");
    }

    public void FingerReconizerTest(Hand hand) {
        if(hand == null) {
            System.out.println("Hand null");
            return;
        }
        if(!hand.isValid()) {
            System.out.println("Hand not valid");
            return;
        }

        System.out.println("\n");

        System.out.println("fingersCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerReconizer.fingersCurve(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersOut : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerReconizer.fingersOut(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersBend : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerReconizer.fingersBend(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n");

        System.out.println("fingersStartCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerReconizer.fingersStartCurve(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersStartOut : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerReconizer.fingersStartOut(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersStartBend : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerReconizer.fingersStartBend(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n");

        System.out.println("fingertipsCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerReconizer.fingertipsCurve(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingertipsOut : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerReconizer.fingertipsOut(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }


        System.out.println("fingertipsBend : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerReconizer.fingertipsBend(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n");
    }

    /*
    public void handsInfo(HandList hands) {
        if(hands == null) return;
        if(hands.isEmpty()) return;
        //Appelle pour chaque main présente les infos d'une seule main
        OneHandInfo(hands.get(0));
    }

    public void OneHandInfo(Hand hand) {
        Finger finger = hand.finger(0);
        System.out.println(" ");

        switch(finger.type()) {
            case TYPE_RING -> System.out.println("Ring");
            case TYPE_INDEX -> System.out.println("Index");
            case TYPE_PINKY -> System.out.println("Pinky");
            case TYPE_THUMB -> System.out.println("Thumb");
            case TYPE_MIDDLE -> System.out.println("Middle");
        }

        float pt = fingertipCurve(finger);
        float ps = fingerStartCurve(finger);
        System.out.println("Fingertip curve : " + pt);
        System.out.println("Finger Start curve : " + ps);

/*
        boolean[] fingertipBend = new boolean[5]; //boolean fingertip bend
        boolean[] fingertipOut = new boolean[5]; //boolean finger start bend
        boolean[] fingerStartBend = new boolean[5]; //boolean finger start bend
        boolean[] fingerStartOut = new boolean[5]; //boolean finger start bend

        Finger finger;

        if(hand == null) return;
        if(!hand.isValid()) return;

        for(int i = 0; i < 5; i++) {
            finger = hand.finger(i);
            fingertipBend[i] = fingertipBend(finger);
            fingertipOut[i] = fingertipOut(finger);

            fingerStartBend[i] = fingerStartBend(finger);
            fingerStartOut[i] = fingerStartOut(finger);
        }

        if(handStone(fingertipBend, fingerStartBend)) {
            System.out.println("Stone !");
        }

    }*/
/*
    public boolean handStone(boolean[] fingertipBend, boolean[] fingerStartBend) {
        for(int i = 1; i < 5; i++) {
            System.out.println("tip " + i + " " + fingertipBend[i]);
            if(!fingertipBend[i]) return false;
        }

        //the start of the thumb may not be bend
        for(int i = 1; i < 5; i++) {
            System.out.println("start " + i + " " + fingerStartBend[i]);
            if(!fingerStartBend[i]) return false;
        }

        return true;
    }

    public boolean fingerStartBend(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        float curve = fingerStartCurve(finger);
        System.out.println(finger.id() + " : " + curve);

        if(curve > 85) return true;

        return false;
    }

    public boolean fingerStartOut(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        float curve = fingerStartCurve(finger);

        if(curve < 15) return true;

        return false;
    }

    //The percentage for the thumb are restrict to 0%, 50% and 100% and may not be correct
    // This is due to a probability for a great value that is low
    public float fingerStartCurve(Finger finger) {
        float percentage;
        if(finger == null) return -1;
        if(!finger.isValid() || !finger.isFinger()) return -1;

        if(finger.type() != Finger.Type.TYPE_THUMB) {
            Bone meta = finger.bone(Bone.Type.TYPE_METACARPAL);
            Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL);

            Vector vMetaPrev = meta.prevJoint();
            Vector vProxNext = prox.nextJoint();

            float distance = vMetaPrev.distanceTo(vProxNext);

            float lengthMeta = meta.length();
            float lengthProx = prox.length();


            float taille = lengthMeta + lengthProx;

            float interval = taille-distance - 1; //Since there are imbalance, we use -1

            percentage = interval*100/23; //Generally, when the finger is bend, the interval is ~23
            percentage = percentage > 100 ? 100 : percentage;
            percentage = percentage < 1 ? 0 : percentage;
        } else {
            Hand hand = finger.hand();
            Vector handCenter = hand.sphereCenter();

            Bone proxThumb = finger.bone(Bone.Type.TYPE_PROXIMAL);
            Vector vProxThumb = proxThumb.nextJoint();

            float distance = vProxThumb.distanceTo(handCenter);

            percentage = 0;
            if(distance<180) percentage = 50; //Values have been found by test, but may not be correct
            if(distance<125) percentage = 100; //Values have been found by test, but may not be correct
        }

        return percentage;
    }


    public boolean fingertipBend(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        float curve = fingertipCurve(finger);
        System.out.println(finger.id() + " : " + curve);

        if(curve > 85) return true;

        return false;
    }

    public boolean fingertipOut(Finger finger) {
        if(finger == null) return false;
        if(!finger.isValid() || !finger.isFinger()) return false;

        float curve = fingertipCurve(finger);

        if(curve < 15) return true;

        return false;
    }

    //Calcul the percentage of the curve of the finger
    public float fingertipCurve(Finger finger) {
        if(finger == null) return -1;
        if(!finger.isValid() || !finger.isFinger()) return -1;

        Bone distal = finger.bone(Bone.Type.TYPE_DISTAL);
        Vector vDistalNext = distal.nextJoint();

        Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL);
        Vector vProxPrev = prox.prevJoint();

        float distance = vDistalNext.distanceTo(vProxPrev);

        Bone inter = finger.bone(Bone.Type.TYPE_INTERMEDIATE);
        float FingertypeLenght = distal.length() + prox.length() + inter.length();

        float percentage = 0;
        float interval = 0;
        if(finger.type() != Finger.Type.TYPE_THUMB) {
            interval = FingertypeLenght-distance;
            percentage = (interval)*100/34; //Generally, when the finger is bend, the interval is ~34
        } else {
            interval = FingertypeLenght-distance < 1.5 ? 0 : FingertypeLenght-distance;
            percentage = (interval)*100/25; //Generally, when the thumb is bend, the interval is ~25
        }

        percentage = percentage > 100 ? 100 : percentage;
        percentage = percentage < 1 ? 0 : percentage;

        return percentage;
    }*/
}
