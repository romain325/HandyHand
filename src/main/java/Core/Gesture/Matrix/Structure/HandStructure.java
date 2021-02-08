package Core.Gesture.Matrix.Structure;

import Core.Gesture.Matrix.Normalization.MatrixNormalizer;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;

/**
 * The class of the structure of a Hand, to save positions and the normalizer matrix of this one
 */
public class HandStructure {
    /**
     * The structure for the thumb
     */
    private FingerStructure thumb;
    /**
     * The structure for the index finger
     */
    private FingerStructure index;
    /**
     * The structure for the middle finger
     */
    private FingerStructure middle;
    /**
     * The structure for the ring finger
     */
    private FingerStructure ring;
    /**
     * The structure for the pinky finger
     */
    private FingerStructure pinky;
    /**
     * The vector of the direction of the hand
     */
    private SimpleMatrix direction;
    /**
     * The vector of the palm normal of hand
     */
    private SimpleMatrix palmNormal;
    /**
     * The matrix normalizer of the hand
     */
    private SimpleMatrix normaliser;

    /**
     * A constructor of the HandStructure
     * @param hand The hand that we want to save information of
     * @throws BadAttributeValueExpException If the hand is null or not valid
     */
    public HandStructure(Hand hand) throws BadAttributeValueExpException {
        if(hand == null || !hand.isValid()) throw new BadAttributeValueExpException("Hand as to be not null and valid");

        for(Finger f : hand.fingers()) {
            switch (f.type()) {
                case TYPE_THUMB -> setThumb(new FingerStructure(f));
                case TYPE_INDEX -> setIndex(new FingerStructure(f));
                case TYPE_MIDDLE -> setMiddle(new FingerStructure(f));
                case TYPE_RING -> setRing(new FingerStructure(f));
                case TYPE_PINKY -> setPinky(new FingerStructure(f));
            }
        }

        Vector directionVector = hand.direction();
        SimpleMatrix direction = new SimpleMatrix(4, 1);
        direction.set(0,0, directionVector.getX());
        direction.set(1,0, directionVector.getY());
        direction.set(2,0, directionVector.getZ());
        direction.set(3,0, 1);

        setDirection(direction);

        Vector palmNoramlVector = hand.palmNormal();
        SimpleMatrix palmNoraml = new SimpleMatrix(4, 1);
        palmNoraml.set(0,0, palmNoramlVector.getX());
        palmNoraml.set(1,0, palmNoramlVector.getY());
        palmNoraml.set(2,0, palmNoramlVector.getZ());
        palmNoraml.set(3,0, 1);

        setPalmNormal(palmNoraml);

        setNormaliser(new MatrixNormalizer(hand).getNormalizer());
    }

    /**
     * The getter of the structure of the thumb
     * @return The structure of the thumb
     */
    public FingerStructure getThumb() {
        return thumb;
    }

    /**
     * The getter of the structure of the index finger
     * @return The structure of the index finger
     */
    public FingerStructure getIndex() {
        return index;
    }

    /**
     * The getter of the structure of the middle finger
     * @return The structure of the middle finger
     */
    public FingerStructure getMiddle() {
        return middle;
    }

    /**
     * The getter of the structure of the ring finger
     * @return The structure of the ring finger
     */
    public FingerStructure getRing() {
        return ring;
    }

    /**
     * The getter of the structure of the pinky finger
     * @return The structure of the pinky finger
     */
    public FingerStructure getPinky() {
        return pinky;
    }

    /**
     * The getter of the vector of the direction of the hand
     * @return The vector of the direction of the hand
     */
    public SimpleMatrix getDirection() {
        return direction;
    }

    /**
     * The getter of the vector of the palm normal of hand
     * @return The vector of the palm normal of hand
     */
    public SimpleMatrix getPalmNormal() {
        return palmNormal;
    }

    /**
     * The getter of the matrix normalizer of the hand
     * @return The matrix normalizer of the hand
     */
    public SimpleMatrix getNormaliser() {
        return normaliser;
    }

    /**
     * The setter of the structure of the thumb
     * @param thumb The structure of the thumb
     */
    private void setThumb(FingerStructure thumb) {
        this.thumb = thumb;
    }

    /**
     * The setter of the structure of the index finger
     * @param index The structure of the index finger
     */
    private void setIndex(FingerStructure index) {
        this.index = index;
    }

    /**
     * The setter of the structure of the middle finger
     * @param middle The structure of the middle finger
     */
    private void setMiddle(FingerStructure middle) {
        this.middle = middle;
    }

    /**
     * The setter of the structure of the ring finger
     * @param ring The structure of the ring finger
     */
    private void setRing(FingerStructure ring) {
        this.ring = ring;
    }

    /**
     * The setter of the structure of the pinky finger
     * @param pinky The structure of the pinky finger
     */
    private void setPinky(FingerStructure pinky) {
        this.pinky = pinky;
    }

    /**
     * The setter of the vector of the direction of the hand
     * @param direction The vector of the direction of the hand
     */
    public void setDirection(SimpleMatrix direction) {
        this.direction = direction;
    }

    /**
     * The setter of the vector of the palm normal of hand
     * @param palmNormal The vector of the palm normal of hand
     */
    public void setPalmNormal(SimpleMatrix palmNormal) {
        this.palmNormal = palmNormal;
    }

    /**
     * The setter of the matrix normalizer of the hand
     * @param normaliser The matrix normalizer of the hand
     */
    private void setNormaliser(SimpleMatrix normaliser) {
        this.normaliser = normaliser;
    }
}
