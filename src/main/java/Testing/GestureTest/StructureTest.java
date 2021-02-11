package Testing.GestureTest;

import Core.Gesture.Matrix.SaveLoad.InPutStructure;
import Core.Gesture.Matrix.SaveLoad.OutPutStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonNormalizedView;
import Visual.ProcessingVisual.Skeleton.SkeletonStructureView;
import Visual.Renderer.ProcessingRenderer;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

import javax.management.BadAttributeValueExpException;

public class StructureTest implements Tester {
    @Override
    public void start() {
        structureTest(new Controller());
    }

    private void structureTest(Controller controller){
        Hand hand = null;
        String file = "testHandStructure.obj";

        while(hand == null || !hand.isValid()) {
            hand = controller.frame().hands().get(0);
        }

        try {
            new OutPutStructure().WriteObjectToFile(new HandStructure(hand), file);
        } catch (BadAttributeValueExpException e) {
            e.printStackTrace();
        }

        new ProcessingRenderer(controller, new SkeletonStructureView((HandStructure) new InPutStructure().ReadObjectInFile(file))).show();
    }
}
