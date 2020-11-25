package Testing.VisualTest.SkeletonTest;

import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonView;
import Visual.Renderer.ProcessingRenderer;
import com.leapmotion.leap.Controller;

public class SkeletonTest implements Tester {
    @Override
    public void start() {
        Controller controller = new Controller();
        skeletonTest(controller);
    }

    private void skeletonTest(Controller controller){
        ProcessingRenderer cam = new ProcessingRenderer(controller,new SkeletonView());
        cam.show();
    }
}
