package Core.Gesture;

import Core.Gesture.Matrix.Structure.IDefineStructure;
import Core.Gesture.Matrix.Structure.StructureManager;
import com.leapmotion.leap.Frame;

/**
 * A class to manage some match of gestures
 */
public class GestureMatcher {
    /**
     * All the gestures that exist
     */
    IDefineStructure gestureStructure;

    public GestureMatcher(IDefineStructure gestureStructure) {
        this.gestureStructure= gestureStructure;
    }


    public boolean getResult(Frame frame){
        IDefineStructure iDefineStructure = new StructureManager().getStructureFromFrame(frame);
        if(iDefineStructure == null) return false;
        return new StructureManager().compareWithNormalization(iDefineStructure,gestureStructure, 20);
    }

}
