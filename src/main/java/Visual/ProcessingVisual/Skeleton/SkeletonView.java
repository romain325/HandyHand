package Visual.ProcessingVisual.Skeleton;

import Utils.CallBack.SketchCallback;
import com.leapmotion.leap.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class defining the display of the skeleton on the current detected hand
 */
public class SkeletonView extends SketchCallback {
    LinkedList<Vector> vectorList = new LinkedList<>();
    @Override
    public void render(Frame frame) {
        int palmX, palmZ,boneX,boneZ,bone2X,bone2Z,toolX,toolZ;

        getSketch().background(55);
        var g = getSketch();

        for (Vector v: vectorList) {
            g.fill(148,0,211);
            g.ellipse(v.getX() +250,v.getZ()+250,5,5);
        }

        g.fill(255,255,255);
        for (Hand hand: frame.hands()) {
            // Palm
            palmX = (int) (hand.palmPosition().getX() + 250);
            palmZ = (int) (hand.palmPosition().getZ() + 250);
            g.ellipse( palmX,palmZ,30,30);

            // Finger
            for (Finger finger : hand.fingers()){
                for (Bone.Type type: Bone.Type.values()) {
                    boneX = (int) finger.bone(type).nextJoint().getX() +250;
                    boneZ = (int) finger.bone(type).nextJoint().getZ() + 250;
                    bone2X = (int) finger.bone(type).prevJoint().getX() +250;
                    bone2Z = (int) finger.bone(type).prevJoint().getZ() + 250;
                    g.ellipse(boneX,boneZ, 10,10);
                    g.line(boneX,boneZ, bone2X, bone2Z);
                }
            }

            // Tools
            for(Tool tool : frame.tools()){
                    toolX = (int) (tool.tipPosition().getX() + 250);
                    toolZ = (int) (tool.tipPosition().getZ() + 250);
                    g.ellipse(toolX,toolZ,20,20);
                    if(vectorList.size() > 100){
                        vectorList.removeLast();
                    }
                    vectorList.push(tool.tipPosition());
            }


        }
    }
}
