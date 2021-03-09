package Visual.ProcessingVisual.Skeleton;

import Core.Gesture.Matrix.Normalization.MatrixNormalizer;
import Utils.CallBack.SketchCallback;
import com.leapmotion.leap.*;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;
import java.util.LinkedList;

/**
 * A class to display the skeleton of hands normalized
 */
public class SkeletonNormalizedView extends SketchCallback {
    /**
     * A list of vector to memorise latest positions of a tool
     */
    LinkedList<Vector> vectorList = new LinkedList<>();

    /**
     * Use the getSketch function to render on the sketch
     * The method to display each frame
     * @param frame Current Frame
     */
    @Override
    public void render(Frame frame) {
        float palmX, palmY, palmZ,boneX,boneY,boneZ,bone2X,bone2Y,bone2Z,toolX,toolY,toolZ;

        getSketch().background(55);
        var g = getSketch();

        for (Vector v: vectorList) {
            g.fill(148,0,211);
            g.ellipse(v.getX() +250,v.getZ()+250,5,5);
        }

        MatrixNormalizer matrixNormalizer;
        g.fill(255,255,255);
        for (Hand hand: frame.hands()) {
            if(!hand.isValid()) continue;
            try {
                matrixNormalizer = new MatrixNormalizer(hand);
            } catch (BadAttributeValueExpException e) {
                continue;
            }
            SimpleMatrix normalizer = matrixNormalizer.getNormalizer();

            // Palm
            palmX = hand.palmPosition().getX();
            palmY = hand.palmPosition().getY();
            palmZ = hand.palmPosition().getZ();

            SimpleMatrix palm = new SimpleMatrix(4,1);
            palm.set(0,0,palmX); palm.set(1,0,palmY); palm.set(2,0,palmZ); palm.set(3,0,1);

            palm = normalizer.mult(palm);

            palmX = (float) palm.get(0,0);
            palmY = (float) palm.get(1,0);
            palmZ = (float) palm.get(2,0);

            palmX += 250;
            palmY += 250;
            palmZ += 250;

            g.ellipse( palmX,palmZ,30,30);

            // Finger
            SimpleMatrix bone = new SimpleMatrix(4,1);
            SimpleMatrix bone2 = new SimpleMatrix(4,1);
            for (Finger finger : hand.fingers()){
                for (Bone.Type type: Bone.Type.values()) {
                    boneX = finger.bone(type).nextJoint().getX();
                    boneY = finger.bone(type).nextJoint().getY();
                    boneZ = finger.bone(type).nextJoint().getZ();

                    bone.set(0,0,boneX); bone.set(1,0,boneY); bone.set(2,0,boneZ); bone.set(3,0,1);

                    bone = normalizer.mult(bone);

                    boneX = (float) bone.get(0,0);
                    boneY = (float) bone.get(1,0);
                    boneZ = (float) bone.get(2,0);

                    bone2X = finger.bone(type).prevJoint().getX();
                    bone2Y = finger.bone(type).prevJoint().getY();
                    bone2Z = finger.bone(type).prevJoint().getZ();

                    bone2.set(0,0,bone2X); bone2.set(1,0,bone2Y); bone2.set(2,0,bone2Z); bone2.set(3,0,1);

                    bone2 = normalizer.mult(bone2);

                    bone2X = (float) bone2.get(0,0);
                    bone2Y = (float) bone2.get(1,0);
                    bone2Z = (float) bone2.get(2,0);

                    boneX += 250;
                    boneY += 250;
                    boneZ += 250;

                    bone2X += 250;
                    bone2Y += 250;
                    bone2Z += 250;

                    g.ellipse(boneX,boneZ, 10,10);
                    g.line(boneX,boneZ, bone2X, bone2Z);
                }
            }

            SimpleMatrix toolVector = new SimpleMatrix(4,1);
            // Tools
            for(Tool tool : frame.tools()){
                toolX = (int) (tool.tipPosition().getX());
                toolY = (int) (tool.tipPosition().getY());
                toolZ = (int) (tool.tipPosition().getZ());

                toolVector.set(0,0,toolX); toolVector.set(1,0,toolY); toolVector.set(2,0,toolZ); toolVector.set(3,0,1);

                toolVector = normalizer.mult(toolVector);

                toolX = (float) toolVector.get(0,0);
                toolY = (float) toolVector.get(1,0);
                toolZ = (float) toolVector.get(2,0);

                toolX += 250;
                toolY += 250;
                toolZ += 250;

                g.ellipse(toolX,toolZ,20,20);
                if(vectorList.size() > 100){
                    vectorList.removeLast();
                }
                vectorList.push(tool.tipPosition());
            }
        }
    }
}
