package Core.Listener;

import com.leapmotion.leap.*;

/**
 * Customized listener from Leap Motion lib
 */
public class LmListener extends Listener {

    /**
     * Method called on the initialization of the controller for accepting policies
     * @param controller controller that have been initialized
     */
    public void onConnect(Controller controller) {
        for (Gesture.Type v :Gesture.Type.values()) {
            controller.enableGesture(v);
        }
        controller.setPolicy(Controller.PolicyFlag.POLICY_IMAGES);
    }

    /**
     * Method executed on each frames
     * @param controller controller capturing the frames
     */
    public void onFrame(Controller controller) {
        Frame frame = controller.frame();
        getPos(frame);
    }

    /**
     * Method determining if the controller is active
     * @return the state of the controller
     */
    public boolean isActive() {
        return false;
    }

    /**
     * Method to get the image from the frame
     * @param frame current frame
     */
    public void getImage(Frame frame){
        ImageList images = frame.images();
        for (Image img : images){

        }
    }

    /**
     * Method to get the current position of the hand using method already available from Leap motion lib
     * @param frame current frame
     */
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
