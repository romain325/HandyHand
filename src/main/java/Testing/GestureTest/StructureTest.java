package Testing.GestureTest;

import Core.Gesture.Matrix.SaveLoad.InPutStructure;
import Core.Gesture.Matrix.SaveLoad.OutPutStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonStructureView;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

import javax.management.BadAttributeValueExpException;

public class StructureTest implements Tester {
    @Override
    public void start() {
        Controller controller = new Controller();
        structureTest(controller);
    }

    private void structureTest(Controller controller){
        Hand hand = null;
        Frame frame = null;
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

        HandStructure handStructure = (HandStructure) new InPutStructure().ReadObjectInFile(file);

//        new SkeletonStructureView().render(handStructure);

        HandStructure secondHandStructure;
        while(true) {
            frame = controller.frame();
            hand = frame.hands().get(0);
            if(hand == null || !hand.isValid()) continue;

            try {
                secondHandStructure = new HandStructure(hand);
            } catch (BadAttributeValueExpException e) {
                continue;
            }

            float divergence = 80;

            boolean comparison = handStructure.compare(secondHandStructure, divergence);

            System.out.println(comparison);
        }
    }
}
