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

    /**
     * A constructor of the class GestureMatcher
     * @param gestureStructure The Structure that are compared with others in a frame
     */
    public GestureMatcher(IDefineStructure gestureStructure) {
        this.gestureStructure= gestureStructure;
    }

    /**
     * A method to know if the gesture in the gestureStructure of the class is done in the frame
     * @param frame The frame from which we want to know if the gesture are done within
     * @return True if the gesture are done in the frame, false otherwise
     */
    public boolean getResult(Frame frame){
        IDefineStructure iDefineStructure = new StructureManager().getStructureFromFrame(frame);
        if(iDefineStructure == null) return false;
        return new StructureManager().compareWithNormalization(iDefineStructure,gestureStructure, 20);
    }

}
