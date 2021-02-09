package Visual.ProcessingVisual.Skeleton;

import Core.Gesture.Matrix.Normalization.MatrixNormalizer;
import Core.Gesture.Matrix.Structure.BoneStructure;
import Core.Gesture.Matrix.Structure.FingerStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import com.leapmotion.leap.*;
import org.ejml.simple.SimpleMatrix;
import processing.core.PApplet;

import java.util.LinkedList;

public class SkeletonStructureView {
    PApplet g;
    LinkedList<Vector> vectorList = new LinkedList<>();

    public void render(HandStructure handStructure) {
        float palmX, palmY, palmZ;

        g = new PApplet();
        g.size(500,500);
        g.background(55);

        for (Vector v: vectorList) {
            g.fill(148,0,211);
            g.ellipse(v.getX() +250,v.getZ()+250,5,5);
        }

        g.fill(255,255,255);

        if(handStructure == null) return;

        SimpleMatrix normalizer = handStructure.getNormaliser();

        // Palm
        SimpleMatrix palm = handStructure.getPalmNormal();

        palm = normalizer.mult(palm);

        palmX = (float) palm.get(0,0);
        palmY = (float) palm.get(1,0);
        palmZ = (float) palm.get(2,0);

        palmX += 250;
        palmY += 250;
        palmZ += 250;

        g.ellipse( palmX,palmZ,30,30);

        // Finger
        float boneNextX,boneNextY,boneNextZ,bonePrevX,bonePrevY,bonePrevZ;
        SimpleMatrix boneNext;
        SimpleMatrix bonePrev;
        for (FingerStructure fingerStructure : handStructure.getFingersStructure()){
            for(BoneStructure boneStructure : fingerStructure.getBonesStructure()) {
                boneNext = boneStructure.getNextJoint();
                boneNext = normalizer.mult(boneNext);

                boneNextX = (float) boneNext.get(0,0);
                boneNextY = (float) boneNext.get(1,0);
                boneNextZ = (float) boneNext.get(2,0);

                bonePrev = boneStructure.getPrevJoint();
                bonePrev = normalizer.mult(bonePrev);

                bonePrevX = (float) bonePrev.get(0,0);
                bonePrevY = (float) bonePrev.get(1,0);
                bonePrevZ = (float) bonePrev.get(2,0);

                boneNextX += 250; boneNextY += 250; boneNextZ += 250;
                bonePrevX += 250; bonePrevY += 250; bonePrevZ += 250;

                g.ellipse(boneNextX,boneNextZ, 10,10);
                g.line(boneNextX,boneNextZ, bonePrevX, bonePrevZ);
            }
        }

        g.draw();
    }
}
