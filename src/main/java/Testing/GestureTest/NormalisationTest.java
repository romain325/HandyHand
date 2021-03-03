package Testing.GestureTest;

import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonNormalizedView;
import Visual.Renderer.ProcessingRenderer;
import com.leapmotion.leap.Controller;

/**
 * The class to test the normalisation of Hands
 */
public class NormalisationTest implements Tester {
    /**
     * The method to init the test
     */
    @Override
    public void start() {
        Controller controller = new Controller();
        normalisationTest(controller);
    }

    /**
     * The method containing the tests
     * @param controller The controller from which we get information of frames
     */
    private void normalisationTest(Controller controller){
        ProcessingRenderer cam = new ProcessingRenderer(controller,new SkeletonNormalizedView());
        cam.show();
    }
}
