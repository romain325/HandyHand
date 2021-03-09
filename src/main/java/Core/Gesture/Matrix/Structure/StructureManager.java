package Core.Gesture.Matrix.Structure;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import org.springframework.lang.Nullable;

import javax.management.BadAttributeValueExpException;

/**
 * A class to manage some Structure
 */
public class StructureManager {

    /**
     * This method allow us to get a Structure from a frame
     * @param frame THe frame from which we want the structure
     * @return The Structure from the frame, or null if something went wrong
     */
    @Nullable
    public IDefineStructure getStructureFromFrame(Frame frame) {
        if(frame == null || !frame.isValid()) return null;

        IDefineStructure iDefineStructure = null;
        HandList hands = frame.hands();

        //When there are only one hand in the frame
        if(hands.count() == 1) {
            try {
                iDefineStructure = new HandStructure(hands.get(0));
            } catch (BadAttributeValueExpException e) {
                return null;
            }
        }
        //When there are two hands in the frame
        else if(hands.count() == 2) {
            Hand hand1 = hands.get(0);
            Hand hand2 = hands.get(1);

            //If the first hand is left, and the second is right
            if(hand1.isLeft() && hand2.isRight()) {
                try {
                    iDefineStructure = new DoubleHandStructure(hand1, hand2);
                } catch (BadAttributeValueExpException e) {
                    return null;
                }
            }
            //If the first hand is right, and the second is left
            else if(hand1.isRight() && hand2.isLeft()) {
                try {
                    iDefineStructure = new DoubleHandStructure(hand2, hand1);
                } catch (BadAttributeValueExpException e) {
                    return null;
                }
            }
            //If hands are of the same type
            else return null;
        } else return null;

        return iDefineStructure;
    }

    /**
     * A method to compare two IDefineStructure to know if they are similar (but without include the distance between hands for the DoubleHandStructure)
     * @param iDefineStructure1 The first IDefineStructure that we want to compare
     * @param iDefineStructure2 The second IDefineStructure that we want to compare
     * @param divergence The divergence that we accept between both IDefineStructure
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithoutDistance(IDefineStructure iDefineStructure1, IDefineStructure iDefineStructure2, float divergence) {
        if(iDefineStructure1 instanceof HandStructure && iDefineStructure2 instanceof HandStructure) {
            return ((HandStructure) iDefineStructure1).compare(((HandStructure) iDefineStructure2), divergence);
        }
        else if(iDefineStructure1 instanceof DoubleHandStructure && iDefineStructure2 instanceof DoubleHandStructure) {
            try {
                return ((DoubleHandStructure) iDefineStructure1).compareWithoutDistance(((DoubleHandStructure) iDefineStructure2), divergence);
            } catch (BadAttributeValueExpException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * A method to compare two IDefineStructure to know if they are similar
     * @param iDefineStructure1 The first IDefineStructure that we want to compare
     * @param iDefineStructure2 The second IDefineStructure that we want to compare
     * @param divergence The divergence that we accept between both IDefineStructure
     * @return Return true if they are similar, false otherwise
     */
    public boolean compare(IDefineStructure iDefineStructure1, IDefineStructure iDefineStructure2, float divergence) {
        if(iDefineStructure1 instanceof HandStructure && iDefineStructure2 instanceof HandStructure) {
            return ((HandStructure) iDefineStructure1).compare(((HandStructure) iDefineStructure2), divergence);
        }
        else if(iDefineStructure1 instanceof DoubleHandStructure && iDefineStructure2 instanceof DoubleHandStructure) {
            try {
                return ((DoubleHandStructure) iDefineStructure1).compare(((DoubleHandStructure) iDefineStructure2), divergence);
            } catch (BadAttributeValueExpException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * A method to compare two IDefineStructure to know if they are similar (but with hands normalized and without include the distance between hands for the DoubleHandStructure)
     * @param iDefineStructure1 The first IDefineStructure that we want to compare
     * @param iDefineStructure2 The second IDefineStructure that we want to compare
     * @param divergence The divergence that we accept between both IDefineStructure
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithNormalizationWithoutDistance(IDefineStructure iDefineStructure1, IDefineStructure iDefineStructure2, float divergence) {
        if(iDefineStructure1 instanceof HandStructure && iDefineStructure2 instanceof HandStructure) {
            return ((HandStructure) iDefineStructure1).compareWithNormalization(((HandStructure) iDefineStructure2), divergence);
        }
        else if(iDefineStructure1 instanceof DoubleHandStructure && iDefineStructure2 instanceof DoubleHandStructure) {
            try {
                return ((DoubleHandStructure) iDefineStructure1).compareWithNormalizationWithoutDistance(((DoubleHandStructure) iDefineStructure2), divergence);
            } catch (BadAttributeValueExpException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * A method to compare two IDefineStructure to know if they are similar (but with hands normalized for the DoubleHandStructure)
     * @param iDefineStructure1 The first IDefineStructure that we want to compare
     * @param iDefineStructure2 The second IDefineStructure that we want to compare
     * @param divergence The divergence that we accept between both IDefineStructure
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithNormalization(IDefineStructure iDefineStructure1, IDefineStructure iDefineStructure2, float divergence) {
        if(iDefineStructure1 instanceof HandStructure && iDefineStructure2 instanceof HandStructure) {
            return ((HandStructure) iDefineStructure1).compareWithNormalization(((HandStructure) iDefineStructure2), divergence);
        }
        else if(iDefineStructure1 instanceof DoubleHandStructure && iDefineStructure2 instanceof DoubleHandStructure) {
            try {
                return ((DoubleHandStructure) iDefineStructure1).compareWithNormalization(((DoubleHandStructure) iDefineStructure2), divergence);
            } catch (BadAttributeValueExpException e) {
                return false;
            }
        }
        return false;
    }
}
