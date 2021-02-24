package Core.Gesture.Matrix.Structure;

import Core.Gesture.Matrix.MatrixUtils;
import Core.Gesture.Matrix.Normalization.MatrixNormalizer;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import org.ejml.simple.SimpleMatrix;
import org.springframework.lang.Nullable;

import javax.management.BadAttributeValueExpException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class of the structure of a Hand, to save positions and the normalizer matrix of this one
 */
public class HandStructure implements Serializable, IDefineStructure {
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
    private double[] direction = new double[4];
    /**
     * The vector of the palm normal of the hand
     */
    private double[] palmNormal = new double[4];
    /**
     * The type of the hand. If it is a right or left hand
     */
    private HandType handType;
    /**
     * The matrix normalizer of the hand
     */
    private double[][] normalizer = new double[4][4];


    public HandStructure(){}

    /**
     * A constructor of the HandStructure
     * All matrix are homogeneous
     * @param hand The hand that we want to save information of
     * @throws BadAttributeValueExpException If the hand is null or not valid
     */
    public HandStructure(Hand hand) throws BadAttributeValueExpException {
        if (hand == null || !hand.isValid())
            throw new BadAttributeValueExpException("Hand as to be not null and valid");

        for (Finger f : hand.fingers()) {
            switch (f.type()) {
                case TYPE_THUMB:
                    setThumb(new FingerStructure(f));
                    break;
                case TYPE_INDEX:
                    setIndex(new FingerStructure(f));
                    break;
                case TYPE_MIDDLE:
                    setMiddle(new FingerStructure(f));
                    break;
                case TYPE_RING:
                    setRing(new FingerStructure(f));
                    break;
                case TYPE_PINKY:
                    setPinky(new FingerStructure(f));
                    break;
            }
        }

        Vector directionVector = hand.direction();
        SimpleMatrix direction = new SimpleMatrix(4, 1);
        direction.set(0, 0, directionVector.getX());
        direction.set(1, 0, directionVector.getY());
        direction.set(2, 0, directionVector.getZ());
        direction.set(3, 0, 1);

        setDirection(direction);

        Vector palmNormalVector = hand.palmNormal();
        SimpleMatrix palmNormal = new SimpleMatrix(4, 1);
        palmNormal.set(0, 0, palmNormalVector.getX());
        palmNormal.set(1, 0, palmNormalVector.getY());
        palmNormal.set(2, 0, palmNormalVector.getZ());
        palmNormal.set(3, 0, 1);

        setPalmNormal(palmNormal);

        handType = hand.isRight() ? HandType.RIGHT : HandType.LEFT;

        setNormalizer(new MatrixNormalizer(hand).getNormalizer());
    }

    /**
     * A constructor of the HandStructure
     * All matrix are homogeneous
     * @param thumb The structure for the thumb
     * @param index The structure for the index
     * @param middle The structure for the middle
     * @param ring The structure for the ring
     * @param pinky The structure for the pinky
     * @param direction The vector of the direction of the hand
     * @param palmNormal The vector of the palm normal of the hand
     * @param handType The type of the hand. If it is a right or left hand
     * @param normalizer The matrix normalizer of the hand
     * @throws BadAttributeValueExpException If arguments are null or not good
     */
    public HandStructure(FingerStructure thumb, FingerStructure index, FingerStructure middle, FingerStructure ring,
                         FingerStructure pinky, SimpleMatrix direction, SimpleMatrix palmNormal,
                         HandType handType, SimpleMatrix normalizer) throws BadAttributeValueExpException {
        if (thumb == null) throw new BadAttributeValueExpException("FingerStructure of thumb as to be not null");
        if (index == null) throw new BadAttributeValueExpException("FingerStructure of index as to be not null");
        if (middle == null) throw new BadAttributeValueExpException("FingerStructure of middle as to be not null");
        if (ring == null) throw new BadAttributeValueExpException("FingerStructure of ring as to be not null");
        if (pinky == null) throw new BadAttributeValueExpException("FingerStructure of pinky as to be not null");

        if (thumb.getType() != Finger.Type.TYPE_THUMB)
            throw new BadAttributeValueExpException("FingerStructure of thumb as to be of the same type");
        if (index.getType() != Finger.Type.TYPE_INDEX)
            throw new BadAttributeValueExpException("FingerStructure of index as to be of the same type");
        if (middle.getType() != Finger.Type.TYPE_MIDDLE)
            throw new BadAttributeValueExpException("FingerStructure of middle as to be of the same type");
        if (ring.getType() != Finger.Type.TYPE_RING)
            throw new BadAttributeValueExpException("FingerStructure of ring as to be of the same type");
        if (pinky.getType() != Finger.Type.TYPE_PINKY)
            throw new BadAttributeValueExpException("FingerStructure of pinky as to be of the same type");

        if (direction == null) throw new BadAttributeValueExpException("Direction matrix has to be not null");
        if (direction.numRows() != 4 || direction.numCols() != 1)
            throw new BadAttributeValueExpException("Direction matrix has to be of size (4,1)");

        if (palmNormal == null) throw new BadAttributeValueExpException("PalmNormal matrix has to be not null");
        if (palmNormal.numRows() != 4 || palmNormal.numCols() != 1)
            throw new BadAttributeValueExpException("PalmNormal matrix has to be of size (4,1)");

        if (handType == null) throw new BadAttributeValueExpException("HandType has to be not null");

        if (normalizer == null) throw new BadAttributeValueExpException("Normalization matrix has to be not null");
        if (normalizer.numRows() != 4 || normalizer.numCols() != 4)
            throw new BadAttributeValueExpException("Normalization matrix has to be of size (4,4)");

        setThumb(thumb);
        setIndex(index);
        setMiddle(middle);
        setRing(ring);
        setPinky(pinky);

        setDirection(direction);
        setPalmNormal(palmNormal);

        setHandType(handType);

        setNormalizer(normalizer);
    }

    /**
     * A method to get all the fingers structure of this hand structure
     * @return All the fingers structure of this hand structure
     */
    public List<FingerStructure> getFingersStructure() {
        List<FingerStructure> lf = new ArrayList<>();

        lf.add(getThumb());
        lf.add(getIndex());
        lf.add(getMiddle());
        lf.add(getRing());
        lf.add(getPinky());

        return lf;
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
        return MatrixUtils.toSimpleMatrix(this.direction);
    }

    /**
     * The getter of the vector of the palm normal of hand
     * @return The vector of the palm normal of hand
     */
    public SimpleMatrix getPalmNormal() {
        return MatrixUtils.toSimpleMatrix(this.palmNormal);
    }

    /**
     * The getter of the type of the hand
     * @return The type of the hand
     */
    public HandType getHandType() {
        return handType;
    }

    /**
     * The getter of the matrix normalizer of the hand
     * @return The matrix normalizer of the hand
     */
    public SimpleMatrix getNormalizer() {
        return MatrixUtils.toSimpleMatrix(this.normalizer);
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
        this.direction = MatrixUtils.fromSimpleMatrix(direction);
    }

    /**
     * The setter of the vector of the palm normal of hand
     * @param palmNormal The vector of the palm normal of hand
     */
    public void setPalmNormal(SimpleMatrix palmNormal) {
        this.palmNormal =  MatrixUtils.fromSimpleMatrix(palmNormal);
    }

    /**
     * The setter of the type of the hand
     * @param handType The type of the hand
     */
    public void setHandType(HandType handType) {
        this.handType = handType;
    }

    /**
     * The setter of the matrix normalizer of the hand
     * @param normalizer The matrix normalizer of the hand
     */
    private void setNormalizer(SimpleMatrix normalizer) {
        this.normalizer = MatrixUtils.squareFromSimpleMatrix(normalizer);
    }

    /**
     * A method to know if two HandStructure are equals
     * @param o The Object that we want to compare with
     * @return Return true if they are equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandStructure that = (HandStructure) o;
        return Objects.equals(thumb, that.thumb) && Objects.equals(index, that.index) && Objects.equals(middle, that.middle) && Objects.equals(ring, that.ring) && Objects.equals(pinky, that.pinky) && Objects.equals(direction, that.direction) && Objects.equals(palmNormal, that.palmNormal) && handType == that.handType && Objects.equals(normalizer, that.normalizer);
    }

    /**
     * A method to compare two HandStructure to know if they are similar
     * @param handStructure The HandStructure that we want to compare with
     * @param divergence The divergence that we accept between both HandStructures
     * @return Return true if they are similar, false otherwise
     */
    public boolean compare(HandStructure handStructure, float divergence) {
        if (handStructure == null || handStructure.getHandType() != this.getHandType()) return false;

        divergence = Math.abs(divergence);

        if (!this.getThumb().compare(handStructure.getThumb(), divergence)) return false;
        if (!this.getIndex().compare(handStructure.getIndex(), divergence)) return false;
        if (!this.getMiddle().compare(handStructure.getMiddle(), divergence)) return false;
        if (!this.getRing().compare(handStructure.getRing(), divergence)) return false;
        if (!this.getPinky().compare(handStructure.getPinky(), divergence)) return false;

        SimpleMatrix direct = handStructure.getDirection();
        for (int i = 0; i < 3; i++) {
            if (Math.abs(direct.get(i) - this.getDirection().get(i)) > divergence) return false;
        }

        SimpleMatrix palm = handStructure.getPalmNormal();
        for (int i = 0; i < 3; i++) {
            if (Math.abs(palm.get(i) - this.getPalmNormal().get(i)) > divergence) return false;
        }

        return true;
    }

    /**
     * A method to get a HandStructure with this one but normalized
     * @return The new HandStructure normalized with is own matrix normalizer
     */
    @Nullable
    public HandStructure getNormalizedHandStructure() {
        try {
            FingerStructure thumbNew = getThumb().getNormalizedFingerStructure(getNormalizer());
            FingerStructure indexNew = getIndex().getNormalizedFingerStructure(getNormalizer());
            FingerStructure middleNew = getMiddle().getNormalizedFingerStructure(getNormalizer());
            FingerStructure ringNew = getRing().getNormalizedFingerStructure(getNormalizer());
            FingerStructure pinkyNew = getPinky().getNormalizedFingerStructure(getNormalizer());

            return new HandStructure(thumbNew, indexNew, middleNew, ringNew, pinkyNew, getDirection(), getPalmNormal(), getHandType(), getNormalizer());
        } catch (BadAttributeValueExpException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * A method to compare two HandStructure to know if they are similar when they are normalized
     * @param handStructure The HandStructure that we want to compare with
     * @param divergence The divergence that we accept between both HandStructures
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithNormalization(HandStructure handStructure, float divergence) {
        HandStructure thisNormalized = getNormalizedHandStructure();
        HandStructure otherNormalized = handStructure.getNormalizedHandStructure();

        if(thisNormalized != null && otherNormalized != null) {
            return thisNormalized.compare(otherNormalized, divergence);
        }
        return false;
    }
}
