package Testing.GestureTest;

import Core.Listener.GestureListener;
import Testing.Tester;
import com.leapmotion.leap.Controller;

import java.io.IOException;

public class GestureTest implements Tester {
    @Override
    public void start() {

        GestureListener listener = new GestureListener();
        Controller controller = new Controller();

        controller.addListener(listener);
        System.out.println("Press Enter to quit!");
        try {
            System.out.println();
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.removeListener(listener);
    }
}
