package Testing.CameraTest;

import Core.Listener.MainListener;
import Visual.ProcessingVisual.CameraView;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;

public class CameraViewTest {


    public void start() throws InterruptedException {

        MainListener listener = new MainListener() {
            @Override
            public void onConnect(Controller controller) {
                System.out.println("coul");
            }

            @Override
            public void onFrame(Controller controller) {
                Frame f = controller.frame();
                this.limitFrameRate(f);
                //test.newImage(f);
            }
        };
        Controller controller = new Controller();

        controller.addListener(listener);

        Thread.sleep(1000);

        controller.removeListener(listener);

    }
}
