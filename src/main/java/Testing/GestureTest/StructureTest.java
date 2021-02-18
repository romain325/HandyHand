package Testing.GestureTest;

import Core.Gesture.Matrix.SaveLoad.InPutStructure;
import Core.Gesture.Matrix.SaveLoad.OutPutStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Testing.Tester;
import Visual.ProcessingVisual.Skeleton.SkeletonStructureView;
import Visual.Renderer.ProcessingRenderer;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

import javax.management.BadAttributeValueExpException;
import java.awt.event.KeyEvent;

public class StructureTest implements Tester {
    private SkeletonStructureView skeletonStructureView;
    private Controller contro;

    @Override
    public void start() {
        structureTest(new Controller());
    }

    private void structureTest(Controller controller){
        contro = controller;
        Hand hand = null;
        Frame frame = null;
        String file = "testHandStructure";
        int count = 0;

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

//        HandStructure secondHandStructure;
//        while(true) {
//            frame = controller.frame();
//            hand = frame.hands().get(0);
//            if(hand == null || !hand.isValid()) continue;
//
//            try {
//                secondHandStructure = new HandStructure(hand);
//            } catch (BadAttributeValueExpException e) {
//                continue;
//            }
//
//            float divergence = 80;
//
//            boolean comparison = handStructure.compare(secondHandStructure, divergence);
//
//            System.out.println(comparison);
//        }

        skeletonStructureView = new SkeletonStructureView((HandStructure) new InPutStructure().ReadObjectInFile(file));

        new ProcessingRenderer(controller, skeletonStructureView).show();
    }
}
