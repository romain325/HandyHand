
package Core.Listener;

import Core.Gesture.Finger.FingerCurveCalculator;
import Core.Gesture.GestureMatcher;
import Core.Gesture.HandPoses.HandType;
import com.leapmotion.leap.*;

public class GestureListener extends MainListener {
    String key;
    HandType handType;

    private static boolean scriptExist(String key){
        return GestureMatcher.gestureExist(key);
    }

    private GestureListener(String key, HandType handType) {
        this.key = key;
        this.handType = handType;
    }

    public static GestureListener GestureListenerFactory(String form) {
        return GestureListener.GestureListenerFactory(form, HandType.BOTH);
    }
    public static GestureListener GestureListenerFactory(String form, HandType handType) {
        return new GestureListener(form, handType);
    }

    @Override
    public void action(Frame frame) {
        FingerList fl = frame.hands().get(0).fingers();
        Finger finger = fl.get(0);
        FingerCurveCalculator fcc = new FingerCurveCalculator();

        for (Finger f: fl) {
            System.out.println(f.type() + " : " + fcc.fingerTipCurve(f));
        }

        //new FingerCurveCalculator().angleDistalIntermediate(finger);
        //new FingerCurveCalculator().angleIntermediateProximal(finger);
        //new FingerCurveCalculator().angleProximalMetacarpal(finger);
        //isActive = GestureMatcher.getResult(this.key,frame, this.handType);
    }


/*
    public void frameInfo(Frame frame) {
        if(!frame.isValid()) {
            System.out.println("Frame not valid");
            return;
        };
        //Appelle les différentes méthodes d'analyse des frames..
        //handsInfo(frame.hands());
        //FingerReconizerTest(frame.hands().get(0));
        //HandReconizerTest(frame.hands().get(0));
        fingertipTest(frame.hands().get(0));
    }

    public void fingertipTest(Hand hand, Method method) {
        if(hand == null || !hand.isValid()) {
            System.out.println("Hand not valid");
            return;
        }

        System.out.println("fingertipsCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerRecognizer.fingertipsCurve(hand).entrySet()) {
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

        System.out.println("isHandClosed : " + HandRecognizer.isHandClosed(hand));
        System.out.println("isHandOpen : " + HandRecognizer.isHandOpen(hand));
        System.out.println("isHandScissors : " + HandRecognizer.isHandScissors(hand));
        System.out.println("countHandFingers : " + HandRecognizer.countHandFingersOut(hand));

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
        for (Map.Entry<Finger.Type, Float> e: FingerRecognizer.fingersCurve(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersOut : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerRecognizer.fingersOut(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersBend : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerRecognizer.fingersBend(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n");

        System.out.println("fingersStartCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerRecognizer.fingersStartCurve(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersStartOut : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerRecognizer.fingersStartOut(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingersStartBend : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerRecognizer.fingersStartBend(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n");

        System.out.println("fingertipsCurve : ");
        for (Map.Entry<Finger.Type, Float> e: FingerRecognizer.fingertipsCurve(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("fingertipsOut : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerRecognizer.fingertipsOut(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }


        System.out.println("fingertipsBend : ");
        for (Map.Entry<Finger.Type, Boolean> e: FingerRecognizer.fingertipsBend(hand).entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n");
    }
*/




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

}
