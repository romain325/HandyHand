package Core.Listener;

import com.leapmotion.leap.*;

public class LmListener extends Listener {
    long lastFrame = 0;
    public long count = 0;

    public void onConnect(Controller controller) {
        for (Gesture.Type v :Gesture.Type.values()) {
            controller.enableGesture(v);
        }
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
    }

    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        if(frame.id() % 2 != 0 || !frame.isValid()) return;
        lastFrame = frame.id();
        getPos(frame);
    }

    public void getImage(Frame frame){
        ImageList images = frame.images();
        for (Image img : images){

        }
    }

    public void getPos(Frame frame){
        if (!frame.hands().isEmpty()) {
            Hand hand = frame.hands().get(0);

            FingerList fingers = hand.fingers();
            if (!fingers.isEmpty()) {
                Vector avgPos = Vector.zero();
                for (Finger finger : fingers) {
                    avgPos = avgPos.plus(finger.tipPosition());
                }
                avgPos = avgPos.divide(fingers.count());
                System.out.println(avgPos);
            }
        }

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            switch (gesture.type()) {
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);
                    System.out.println("Circle");
                    break;
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    System.out.println("Swipe");
                    break;
                case TYPE_SCREEN_TAP:
                    ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
                    System.out.println("Screen Tap");
                    break;
                case TYPE_KEY_TAP:
                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
                    System.out.println("Key Tap");
                    break;
                default:
                    System.out.println("Gesture not recognized");
                    break;
            }
        }
    }
}
