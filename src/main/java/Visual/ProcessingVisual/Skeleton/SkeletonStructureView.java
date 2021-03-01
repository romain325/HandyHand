package Visual.ProcessingVisual.Skeleton;

import Core.Gesture.Matrix.Normalization.MatrixNormalizer;
import Core.Gesture.Matrix.Structure.*;
import Utils.CallBack.SketchCallback;
import com.leapmotion.leap.*;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;

/**
 * A class to display the skeleton of hands stocked in structures
 */
public class SkeletonStructureView extends SketchCallback {
    /**
     * The structure of hands that are constantly compared to the current one
     */
    IDefineStructure defineStructure;

    /**
     * A constructor of the class SkeletonStructureView
     */
    public SkeletonStructureView() {
        setDefineStruct(null);
    }

    /**
     * A constructor of the class SkeletonStructureView
     * @param handStructure The HandStructure that will be constantly compared to the current one
     */
    public SkeletonStructureView(HandStructure handStructure) {
        setDefineStruct(handStructure);
    }

    /**
     * The getter of the structure of hands that are constantly compared to the current one
     * @return The structure of hands that are constantly compared to the current one
     */
    public IDefineStructure getDefineStructure() {
        return defineStructure;
    }

    /**
     * The setter of the structure of hands that are constantly compared to the current one
     * @param defineStruct The structure of hands that are constantly compared to the current one
     */
    private void setDefineStruct(IDefineStructure defineStruct) {
        defineStructure = defineStruct;
    }

    /**
     * Use the getSketch function to render on the sketch
     * The method to display each frame
     * @param frame Current Frame
     */
    @Override
    public void render(Frame frame) {
        getSketch().size(500,500);
        getSketch().background(55);

        if(frame == null) return;

        IDefineStructure iDefineStructure = new StructureManager().getStructureFromFrame(frame);

        if(iDefineStructure == null) return;

        displayDefineStructureAtPosition(iDefineStructure, 250, 400);

        if(defineStructure == null) setDefineStruct(iDefineStructure); //To reset the structure compared

        displayDefineStructureAtPosition(defineStructure, 250, 150);

//        System.out.println(new StructureManager().compareWithNormalization(iDefineStructure, defineStructure, 20));
        System.out.println(new StructureManager().compareWithNormalizationWithoutDistance(iDefineStructure, defineStructure, 20));
    }

    /**
     * A method to display a HandStructure at a known position
     * @param handStructure The HandStructure that we want to display
     * @param posiX The abscissa position
     * @param posiZ The ordinate position
     */
    public void displayHandStructure(HandStructure handStructure, int posiX, int posiZ) {
        float palmX, palmY, palmZ;

        getSketch().fill(255,255,255);

        if(handStructure == null) return;

        SimpleMatrix normalizer = handStructure.getNormalizer();

        // Palm
        SimpleMatrix palm = handStructure.getPalmNormal();

        palmX = (float) palm.get(0,0);
        palmY = (float) palm.get(1,0);
        palmZ = (float) palm.get(2,0);

        palmX += posiX;
        palmY += 250;
        palmZ += posiZ;

        getSketch().ellipse( palmX,palmZ,30,30);

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

                boneNextX += posiX; boneNextY += 250; boneNextZ += posiZ;
                bonePrevX += posiX; bonePrevY += 250; bonePrevZ += posiZ;

                getSketch().ellipse(boneNextX,boneNextZ, 10,10);
                getSketch().line(boneNextX,boneNextZ, bonePrevX, bonePrevZ);
            }
        }
    }

    /**
     * A method to display a DoubleHandStructure at a known position
     * @param doubleHandStructure The DoubleHandStructure that we want to display
     * @param centerX The abscissa position
     * @param centerZ The ordinate position
     */
    public void displayDoubleHandStructure(DoubleHandStructure doubleHandStructure, int centerX, int centerZ) {
        if(doubleHandStructure == null) return;

        int posiRight = (int) (doubleHandStructure.getDistanceNextMetaIndex() / 2);
        int posiLeft = posiRight * -1;

        posiRight += centerX;
        posiLeft += centerX;

        displayHandStructure(doubleHandStructure.getRightHand(), posiRight, centerZ);
        displayHandStructure(doubleHandStructure.getLeftHand(), posiLeft, centerZ);
    }

    /**
     * A method to display a IDefineStructure at a known position
     * @param iDefineStructure The IDefineStructure that we want to display
     * @param centerX The abscissa position
     * @param centerZ The ordinate position
     */
    public void displayDefineStructureAtPosition(IDefineStructure iDefineStructure, int centerX, int centerZ) {
        if(iDefineStructure == null) return;

        if(iDefineStructure instanceof HandStructure) {
            displayHandStructure((HandStructure)iDefineStructure, centerX, centerZ);
        } else if (iDefineStructure instanceof DoubleHandStructure) {
            displayDoubleHandStructure((DoubleHandStructure) iDefineStructure, centerX, centerZ);
        }
    }

    /**
     * A method to display a Frame at a known position
     * @param frame The Frame that we want to display
     * @param posiX The abscissa position
     * @param posiZ The ordinate position
     */
    public void displayFrameAtPositionFixe(Frame frame, int posiX, int posiZ) {
        float palmX, palmY, palmZ,boneX,boneY,boneZ,bone2X,bone2Y,bone2Z,toolX,toolY,toolZ;

        var g = getSketch();

        MatrixNormalizer matrixNormalizer;
        g.fill(255,255,255);
        for (Hand hand: frame.hands()) {
            if (!hand.isValid()) continue;
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

            SimpleMatrix palm = new SimpleMatrix(4, 1);
            palm.set(0, 0, palmX);
            palm.set(1, 0, palmY);
            palm.set(2, 0, palmZ);
            palm.set(3, 0, 1);

            palm = normalizer.mult(palm);

            palmX = (float) palm.get(0, 0);
            palmY = (float) palm.get(1, 0);
            palmZ = (float) palm.get(2, 0);

            palmX += posiX;
            palmZ += posiZ;

            g.ellipse(palmX, palmZ, 30, 30);

            // Finger
            SimpleMatrix bone = new SimpleMatrix(4, 1);
            SimpleMatrix bone2 = new SimpleMatrix(4, 1);
            for (Finger finger : hand.fingers()) {
                for (Bone.Type type : Bone.Type.values()) {
                    boneX = finger.bone(type).nextJoint().getX();
                    boneY = finger.bone(type).nextJoint().getY();
                    boneZ = finger.bone(type).nextJoint().getZ();

                    bone.set(0, 0, boneX);
                    bone.set(1, 0, boneY);
                    bone.set(2, 0, boneZ);
                    bone.set(3, 0, 1);

                    bone = normalizer.mult(bone);

                    boneX = (float) bone.get(0, 0);
                    boneY = (float) bone.get(1, 0);
                    boneZ = (float) bone.get(2, 0);

                    bone2X = finger.bone(type).prevJoint().getX();
                    bone2Y = finger.bone(type).prevJoint().getY();
                    bone2Z = finger.bone(type).prevJoint().getZ();

                    bone2.set(0, 0, bone2X);
                    bone2.set(1, 0, bone2Y);
                    bone2.set(2, 0, bone2Z);
                    bone2.set(3, 0, 1);

                    bone2 = normalizer.mult(bone2);

                    bone2X = (float) bone2.get(0, 0);
                    bone2Y = (float) bone2.get(1, 0);
                    bone2Z = (float) bone2.get(2, 0);

                    boneX += posiX;
                    boneZ += posiZ;

                    bone2X += posiX;
                    bone2Z += posiZ;

                    g.ellipse(boneX, boneZ, 10, 10);
                    g.line(boneX, boneZ, bone2X, bone2Z);
                }
            }
        }
    }

    /**
     * A method to reset the structure that are compared to the current one
     */
    public void resetDefineStructure() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setDefineStruct(null);
    }
}
