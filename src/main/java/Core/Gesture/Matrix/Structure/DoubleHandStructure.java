package Core.Gesture.Matrix.Structure;

import com.leapmotion.leap.Hand;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;

/**
 * The class of the structure of a couple of Hands, to save both positions and the normalizer matrix of those, and the distance between them
 */
public class DoubleHandStructure implements IDefineStructure {
    /**
     * The structure of the left hand
     */
    private HandStructure leftHand;
    /**
     * The structure of the right hand
     */
    private HandStructure rightHand;
    /**
     * The distance between the end of metacarpals bones of index finger of both hands
     */
    private float distanceNextMetaIndex;
    /**
     * The distance between the beginning of metacarpals bones of index finger of both hands
     */
    private float distancePrevMetaIndex;
    /**
     * The distance between the end of metacarpals bones of pinky finger of both hands
     */
    private float distanceNextMetaPinky;
    /**
     * The distance between the beginning of metacarpals bones of pinky finger of both hands
     */
    private float distancePrevMetaPinky;

    public DoubleHandStructure(){
    }

    /**
     * A constructor of the DoubleHandStructure
     * @param leftHand The structure of the left hand
     * @param rightHand The structure of the right hand
     * @throws BadAttributeValueExpException If hands are null, or not of the good type
     */
    public DoubleHandStructure(HandStructure leftHand, HandStructure rightHand) throws BadAttributeValueExpException {
        if(leftHand == null || rightHand == null) throw new BadAttributeValueExpException("Both HandStructure have to be not null");
        if(leftHand.getHandType() != HandType.LEFT || rightHand.getHandType() != HandType.RIGHT)
            throw new BadAttributeValueExpException("Both HandStructure have to be of the correct HandType : Left for the first, right for the second");

        setLeftHand(leftHand);
        setRightHand(rightHand);

        BoneStructure metaIndexLeftHand = leftHand.getIndex().getMetacarpals();
        BoneStructure metaPinkyLeftHand = leftHand.getPinky().getMetacarpals();

        BoneStructure metaIndexRightHand = rightHand.getIndex().getMetacarpals();
        BoneStructure metaPinkyRightHand = rightHand.getPinky().getMetacarpals();

        setDistanceNextMetaIndex(distanceBetween2Vectors(metaIndexLeftHand.getNextJointMatrix(), metaIndexRightHand.getNextJointMatrix()));
        setDistancePrevMetaIndex(distanceBetween2Vectors(metaIndexLeftHand.getPrevJointMatrix(), metaIndexRightHand.getPrevJointMatrix()));

        setDistanceNextMetaPinky(distanceBetween2Vectors(metaPinkyLeftHand.getNextJointMatrix(), metaPinkyRightHand.getNextJointMatrix()));
        setDistancePrevMetaPinky(distanceBetween2Vectors(metaPinkyLeftHand.getPrevJointMatrix(), metaPinkyRightHand.getPrevJointMatrix()));
    }

    /**
     * A constructor of the DoubleHandStructure
     * @param leftHand The left hand
     * @param rightHand The right hand
     * @throws BadAttributeValueExpException If hands are null, or not of the good type
     */
    public DoubleHandStructure(Hand leftHand, Hand rightHand) throws BadAttributeValueExpException {
        if(leftHand == null || rightHand == null || !leftHand.isValid() || !rightHand.isValid())
            throw new BadAttributeValueExpException("Both Hand have to be not null and valid");
        if(!leftHand.isLeft() || !rightHand.isRight())
            throw new BadAttributeValueExpException("Both Hand have to be of the correct type : Left for the first, right for the second");

        HandStructure leftHandStructure = new HandStructure(leftHand);
        HandStructure rightHandStructure = new HandStructure(rightHand);

        setLeftHand(leftHandStructure);
        setRightHand(rightHandStructure);

        BoneStructure metaIndexLeftHand = leftHandStructure.getIndex().getMetacarpals();
        BoneStructure metaPinkyLeftHand = leftHandStructure.getPinky().getMetacarpals();

        BoneStructure metaIndexRightHand = rightHandStructure.getIndex().getMetacarpals();
        BoneStructure metaPinkyRightHand = rightHandStructure.getPinky().getMetacarpals();

        setDistanceNextMetaIndex(distanceBetween2Vectors(metaIndexLeftHand.getNextJointMatrix(), metaIndexRightHand.getNextJointMatrix()));
        setDistancePrevMetaIndex(distanceBetween2Vectors(metaIndexLeftHand.getPrevJointMatrix(), metaIndexRightHand.getPrevJointMatrix()));

        setDistanceNextMetaPinky(distanceBetween2Vectors(metaPinkyLeftHand.getNextJointMatrix(), metaPinkyRightHand.getNextJointMatrix()));
        setDistancePrevMetaPinky(distanceBetween2Vectors(metaPinkyLeftHand.getPrevJointMatrix(), metaPinkyRightHand.getPrevJointMatrix()));
    }

    /**
     * The getter of the structure of the left hand
     * @return The structure of the left hand
     */
    public HandStructure getLeftHand() {
        return leftHand;
    }

    /**
     * The getter of the structure of the right hand
     * @return The structure of the right hand
     */
    public HandStructure getRightHand() {
        return rightHand;
    }

    /**
     * The getter of the distance between the end of metacarpals bones of index finger of both hands
     * @return The distance between the end of metacarpals bones of index finger of both hands
     */
    public float getDistanceNextMetaIndex() {
        return distanceNextMetaIndex;
    }

    /**
     * The getter of the distance between the beginning of metacarpals bones of index finger of both hands
     * @return The distance between the beginning of metacarpals bones of index finger of both hands
     */
    public float getDistancePrevMetaIndex() {
        return distancePrevMetaIndex;
    }

    /**
     * The getter of the distance between the end of metacarpals bones of pinky finger of both hands
     * @return The distance between the end of metacarpals bones of pinky finger of both hands
     */
    public float getDistanceNextMetaPinky() {
        return distanceNextMetaPinky;
    }

    /**
     * The getter of the distance between the beginning of metacarpals bones of pinky finger of both hands
     * @return The distance between the beginning of metacarpals bones of pinky finger of both hands
     */
    public float getDistancePrevMetaPinky() {
        return distancePrevMetaPinky;
    }

    /**
     * The setter of the structure of the left hand
     * @param leftHand The structure of the left hand
     */
    private void setLeftHand(HandStructure leftHand) {
        this.leftHand = leftHand;
    }

    /**
     * The setter of the structure of the right hand
     * @param rightHand The structure of the right hand
     */
    private void setRightHand(HandStructure rightHand) {
        this.rightHand = rightHand;
    }

    /**
     * The setter of the distance between the end of metacarpals bones of index finger of both hands
     * @param distanceNextMetaIndex The distance between the end of metacarpals bones of index finger of both hands
     */
    private void setDistanceNextMetaIndex(float distanceNextMetaIndex) {
        this.distanceNextMetaIndex = distanceNextMetaIndex;
    }

    /**
     * The setter of the distance between the beginning of metacarpals bones of index finger of both hands
     * @param distancePrevMetaIndex The distance between the beginning of metacarpals bones of index finger of both hands
     */
    private void setDistancePrevMetaIndex(float distancePrevMetaIndex) {
        this.distancePrevMetaIndex = distancePrevMetaIndex;
    }

    /**
     * The setter of the distance between the end of metacarpals bones of pinky finger of both hands
     * @param distanceNextMetaPinky The distance between the end of metacarpals bones of pinky finger of both hands
     */
    private void setDistanceNextMetaPinky(float distanceNextMetaPinky) {
        this.distanceNextMetaPinky = distanceNextMetaPinky;
    }

    /**
     * The setter of the distance between the beginning of metacarpals bones of pinky finger of both hands
     * @param distancePrevMetaPinky The distance between the beginning of metacarpals bones of pinky finger of both hands
     */
    private void setDistancePrevMetaPinky(float distancePrevMetaPinky) {
        this.distancePrevMetaPinky = distancePrevMetaPinky;
    }

    /**
     * A method to get the distance between 2 vectors
     * Coordinates are homogeneous, so each vector have a row added
     * @param first The first vector
     * @param second The second vector
     * @return The distance between vectors
     * @throws BadAttributeValueExpException If vectors are null or not of the good size
     */
    public float distanceBetween2Vectors(SimpleMatrix first, SimpleMatrix second) throws BadAttributeValueExpException {
        if(first == null || second == null) throw new BadAttributeValueExpException("Both SimpleMatrix have to be not null");
        if(first.numCols() != 1 || second.numCols() != 1) throw new BadAttributeValueExpException("Both SimpleMatrix have to have one columns");
        if(first.numRows() != second.numRows())  throw new BadAttributeValueExpException("Both SimpleMatrix have to be of the same size");

        float sumSquare = 0;
        for(int i = 0; i < first.numRows() - 1; i++) {
            sumSquare += Math.pow(first.get(i,0) - second.get(i,0), 2);
        }

        return (float) Math.sqrt(sumSquare);
    }

    /**
     * A method to compare two DoubleHandStructure to know if they are similar, but without include the distance between hands
     * @param doubleHandStructure The DoubleHandStructure that we want to compare with
     * @param divergence The divergence that we accept between both DoubleHandStructures
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithoutDistance(DoubleHandStructure doubleHandStructure, float divergence) throws BadAttributeValueExpException {
        if(doubleHandStructure == null) return false;

        if(! doubleHandStructure.getLeftHand().compare(this.getLeftHand(), divergence)) return false;
        if(! doubleHandStructure.getRightHand().compare(this.getRightHand(), divergence)) return false;

        return true;
    }

    /**
     * A method to compare two DoubleHandStructure to know if they are similar
     * @param doubleHandStructure The DoubleHandStructure that we want to compare with
     * @param divergence The divergence that we accept between both DoubleHandStructures
     * @return Return true if they are similar, false otherwise
     */
    public boolean compare(DoubleHandStructure doubleHandStructure, float divergence) throws BadAttributeValueExpException {
        if(doubleHandStructure == null) return false;

        if(! this.compareWithoutDistance(doubleHandStructure, divergence)) return false;

        if(Math.abs(doubleHandStructure.getDistanceNextMetaIndex() - this.getDistanceNextMetaIndex()) > divergence) return false;
        if(Math.abs(doubleHandStructure.getDistancePrevMetaIndex() - this.getDistancePrevMetaIndex()) > divergence) return false;
        if(Math.abs(doubleHandStructure.getDistanceNextMetaPinky() - this.getDistanceNextMetaPinky()) > divergence) return false;
        if(Math.abs(doubleHandStructure.getDistancePrevMetaPinky() - this.getDistancePrevMetaPinky()) > divergence) return false;

        return true;
    }

    /**
     * A method to compare two DoubleHandStructure to know if they are similar, but with hands normalized and without include the distance between hands
     * @param doubleHandStructure The DoubleHandStructure that we want to compare with
     * @param divergence The divergence that we accept between both DoubleHandStructures
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithNormalizationWithoutDistance(DoubleHandStructure doubleHandStructure, float divergence) throws BadAttributeValueExpException {
        if(doubleHandStructure == null) return false;

        if(! doubleHandStructure.getLeftHand().compareWithNormalization(this.getLeftHand(), divergence)) return false;
        if(! doubleHandStructure.getRightHand().compareWithNormalization(this.getRightHand(), divergence)) return false;

        return true;
    }

    /**
     * A method to compare two DoubleHandStructure to know if they are similar, but with hands normalized
     * @param doubleHandStructure The DoubleHandStructure that we want to compare with
     * @param divergence The divergence that we accept between both DoubleHandStructures
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareWithNormalization(DoubleHandStructure doubleHandStructure, float divergence) throws BadAttributeValueExpException {
        if(doubleHandStructure == null) return false;

        if(! this.compareWithNormalizationWithoutDistance(doubleHandStructure, divergence)) return false;

        if(Math.abs(doubleHandStructure.getDistanceNextMetaIndex() - this.getDistanceNextMetaIndex()) > divergence) return false;
        if(Math.abs(doubleHandStructure.getDistancePrevMetaIndex() - this.getDistancePrevMetaIndex()) > divergence) return false;
        if(Math.abs(doubleHandStructure.getDistanceNextMetaPinky() - this.getDistanceNextMetaPinky()) > divergence) return false;
        if(Math.abs(doubleHandStructure.getDistancePrevMetaPinky() - this.getDistancePrevMetaPinky()) > divergence) return false;

        return true;
    }





}
