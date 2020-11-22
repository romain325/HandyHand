package Testing.CameraTest;

import Visual.ProcessingRenderer;
import Visual.ProcessingVisual.CameraView;
import com.leapmotion.leap.Controller;

import java.util.LinkedList;

public class CameraViewTest {


    public void start() throws InterruptedException {
        Controller controller = new Controller();
        var policyFlags = new LinkedList<Controller.PolicyFlag>();
        policyFlags.add(Controller.PolicyFlag.POLICY_IMAGES);

        ProcessingRenderer cam = new ProcessingRenderer(controller,new CameraView(), policyFlags);

        cam.show();

    }
}
