package Testing.CameraTest;

import Testing.Tester;
import Visual.ProcessingRenderer;
import Visual.ProcessingVisual.CameraView;
import com.leapmotion.leap.Controller;

import java.util.LinkedList;

public class CameraViewTest implements Tester {

    @Override
    public void start() {
        Controller controller = new Controller();
        CameraTest(controller);
    }

    private void CameraTest(Controller controller){
        var policyFlags = new LinkedList<Controller.PolicyFlag>();
        policyFlags.add(Controller.PolicyFlag.POLICY_IMAGES);

        ProcessingRenderer cam = new ProcessingRenderer(controller,new CameraView(), policyFlags);

        cam.show();
    }
}
