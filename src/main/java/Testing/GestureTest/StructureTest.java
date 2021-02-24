package Testing.GestureTest;

import Core.Gesture.Matrix.SaveLoad.InPutStructure;
import Core.Gesture.Matrix.SaveLoad.OutPutStructure;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonStructureView;
import Visual.Renderer.ProcessingRendererSkeletonStructureView;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

import javax.management.BadAttributeValueExpException;

/**
 * The class to test all the structure
 */
public class StructureTest implements Tester {
    /**
     * The method to start the test
     */
    @Override
    public void start() {
        structureTest(new Controller());
    }

    /**
     * The method containing the tests
     * @param controller The controller to collect information return by the LeapMotion
     */
    private void structureTest(Controller controller){
        Hand hand = null;
        Frame frame;
        String file = "testHandStructure";

        while(hand == null || !hand.isValid()) {
            frame = controller.frame();
            hand = frame.hands().get(0);
        }

        try {
            new OutPutStructure().WriteObjectToFile(new HandStructure(hand), file);
        } catch (BadAttributeValueExpException e) {
            e.printStackTrace();
        }

        SkeletonStructureView skeletonStructureView = new SkeletonStructureView((HandStructure) new InPutStructure().readObjectInFile(file));

        try {
            new ProcessingRendererSkeletonStructureView(controller, skeletonStructureView).show();
        } catch (BadAttributeValueExpException e) {
            e.printStackTrace();
        }
    }
}
