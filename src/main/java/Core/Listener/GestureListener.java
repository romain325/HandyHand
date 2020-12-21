
package Core.Listener;

import Core.Gesture.Finger.FingerCurveCalculator;
import Core.Gesture.Finger.FingerPositionCalculator;
import Core.Gesture.GestureMatcher;
import Core.Gesture.HandPoses.HandRecognizer;
import Core.Gesture.HandPoses.HandType;
import com.leapmotion.leap.*;

import java.util.Map;

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
//        for (Map.Entry<Finger.Type, Float> e: new FingerCurveCalculator().fingersCurve(frame.hands().get(0)).entrySet()) {
//            System.out.println(e.getKey() + " : " + e.getValue());
//        }

//        Finger.Type type1 = Finger.Type.TYPE_THUMB;
//        Finger.Type type1 = Finger.Type.TYPE_INDEX;
//        Finger.Type type1 = Finger.Type.TYPE_MIDDLE;
//        Finger.Type type1 = Finger.Type.TYPE_RING;
        Finger.Type type1 = Finger.Type.TYPE_PINKY;
        Finger.Type type2 = Finger.Type.TYPE_THUMB;
//        Finger.Type type2 = Finger.Type.TYPE_INDEX;
//        Finger.Type type2 = Finger.Type.TYPE_MIDDLE;
//        Finger.Type type2 = Finger.Type.TYPE_RING;
//        Finger.Type type2 = Finger.Type.TYPE_PINKY;

        if(!frame.hands().get(0).isValid()) return;
//        if(!frame.hands().get(1).isValid()) return;

        Finger finger1 = frame.hands().get(0).fingers().fingerType(type1).get(0);
        Finger finger2 = frame.hands().get(1).fingers().fingerType(type2).get(0);

//        new FingerPositionCalculator().isNextDistalSticks(finger1, finger2);
//        new FingerPositionCalculator().isNextIntermediateSticks(finger1, finger2);

//        System.out.println(new FingerCurveCalculator().fingerTip(finger1));
//        new FingerCurveCalculator().fingerTip(finger1);
//        System.out.println(new FingerCurveCalculator().fingerStart(finger1));

//        System.out.println(new FingerCurveCalculator().fingerCurve(finger1));
//        new FingerCurveCalculator().fingerCurve(finger1);

//        System.out.println(new HandRecognizer().isHandFuck(frame.hands().get(0)));
//        new HandRecognizer().isHandFuck(frame.hands().get(0));
//        System.out.println(new HandRecognizer().isHandOk(frame.hands().get(0)));
//        System.out.println(new HandRecognizer().isHandsHeart(frame.hands()));
//        System.out.println(new HandRecognizer().isHandsO(frame.hands()));
        System.out.println(new HandRecognizer().isHandsJull(frame.hands()));
//        isActive = GestureMatcher.getResult(this.key,frame, this.handType);
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
