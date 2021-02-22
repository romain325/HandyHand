package Testing.GestureTest;

import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonNormalizedView;
import Visual.Renderer.ProcessingRenderer;
import com.leapmotion.leap.Controller;

public class NormalisationTest implements Tester {
    @Override
    public void start() {
        Controller controller = new Controller();
        normalisationTest(controller);
    }

    private void normalisationTest(Controller controller){
        ProcessingRenderer cam = new ProcessingRenderer(controller,new SkeletonNormalizedView());
        cam.show();
    }
}
