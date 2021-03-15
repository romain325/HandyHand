package Testing.VisualTest.CameraTest;

import Testing.Tester;
import Visual.Renderer.ProcessingRenderer;
import Visual.ProcessingVisual.CameraView.CameraView;
import Visual.ProcessingVisual.CameraView.CameraViewNormalized;
import com.leapmotion.leap.Controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to test the view of the camera
 */
public class CameraViewTest implements Tester {
    private List<Controller.PolicyFlag> policyFlags;

    public CameraViewTest(){
        this.policyFlags = new LinkedList<>();
        policyFlags.add(Controller.PolicyFlag.POLICY_IMAGES);
    }

    @Override
    public void start() {
        Controller controller = new Controller();
        cameraTest(controller);
        //cameraNormalizedTest(controller);
    }

    /**
     * Method to display the controller view
     * @param controller current controller
     */
    private void cameraTest(Controller controller){
        ProcessingRenderer cam = new ProcessingRenderer(controller,new CameraView(), policyFlags);
        cam.show();
    }

    /**
     * Method to get the camera view without the distortion
     * @param controller current controller
     */
    private void cameraNormalizedTest(Controller controller){
        HashMap<String,String[]> shaders =  new HashMap<>();
        shaders.put("dewarp", new String[] {this.getClass().getResource("/dewarp.glpl").getPath(), this.getClass().getResource("/passthrough.glpl").getPath()});

        //ProcessingRenderer cam = new ProcessingRenderer(controller,new CameraViewNormalized(), policyFlags,shaders);
        ProcessingRenderer cam = new ProcessingRenderer(controller,new CameraViewNormalized(), policyFlags);
        cam.show();
    }
}
