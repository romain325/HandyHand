package Core.Gesture.Matrix.Structure;

import Core.Gesture.Matrix.MatrixUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Vector;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;
import java.io.Serializable;
import java.util.Objects;

/**
 * The class of the structure of a bone, to save positions of this one
 */
public class BoneStructure implements Serializable {
    /**
     * The position of the end of the bone
     */
    public double[] nextJoint = new double[4];
    /**
     * The position of the start of the bone
     */
    public double[] prevJoint = new double[4];
    /**
     * The type of the bone
     */
    private Bone.Type type;

    public BoneStructure(){}


    /**
     * A constructor of the class BoneStructure
     * @param bone The bone that we want to save informations
     * @throws BadAttributeValueExpException If the bone is null or not valid
     */
    public BoneStructure(Bone bone) throws BadAttributeValueExpException {
        if(bone == null || !bone.isValid()) throw new BadAttributeValueExpException("Bone as to be not null and valid");

        Vector nextJoint = bone.nextJoint();
        this.nextJoint[0] = nextJoint.getX();
        this.nextJoint[1] = nextJoint.getY();
        this.nextJoint[2] = nextJoint.getZ();
        this.nextJoint[3] = 1;

        Vector prevJoint = bone.prevJoint();
        this.prevJoint[0] = prevJoint.getX();
        this.prevJoint[1] = prevJoint.getY();
        this.prevJoint[2] = prevJoint.getZ();
        this.prevJoint[3] = 1;

        setType(bone.type());
    }



    /**
     * A constructor of the class BoneStructure
     * @param type The type of the bone
     * @param nextJoint The position of the end of the bone
     * @param prevJoint The position of the start of the bone
     * @throws BadAttributeValueExpException If attributes are null, or not of the good size (For positions : (4,1)
     */
    public BoneStructure(Bone.Type type, SimpleMatrix nextJoint, SimpleMatrix prevJoint) throws BadAttributeValueExpException {
        setType(type);
        setNextJoint(nextJoint);
        setPrevJoint(prevJoint);
    }

    /**
     * The getter of the position of the end of the bone
     * @return The position of the end of the bone
     */
    @JsonIgnore
    public SimpleMatrix getNextJointMatrix() {
        return MatrixUtils.toSimpleMatrix(this.nextJoint);
    }

    /**
     * The getter of the position of the start of the bone
     * @return The position of the start of the bone
     */
    @JsonIgnore
    public SimpleMatrix getPrevJointMatrix() {
        return MatrixUtils.toSimpleMatrix(this.prevJoint);
    }

    /**
     * The getter of the position of the end of the bone
     * @return The position of the end of the bone
     */
    public double[] getNextJoint() {
        return this.nextJoint;
    }

    /**
     * The getter of the position of the start of the bone
     * @return The position of the start of the bone
     */
    public double[] getPrevJoint() {
        return this.prevJoint;
    }

    /**
     * The getter of the type of the bone
     * @return The type of the bone
     */
    public Bone.Type getType() {
        return type;
    }

    /**
     * The setter of the position of the end of the bone
     * @param nextJoint The position of the end of the bone
     * @throws BadAttributeValueExpException If the position is null or not of size (4,1)
     */
    @JsonIgnore
    private void setNextJoint(SimpleMatrix nextJoint) throws BadAttributeValueExpException {
        if(nextJoint == null) throw new BadAttributeValueExpException("The vector as to be not null");
        if(nextJoint.numRows() != 4 || nextJoint.numCols() != 1) throw new BadAttributeValueExpException("The vector as to be of size (4,1)");
        this.nextJoint = MatrixUtils.fromSimpleMatrix(nextJoint);
    }

    /**
     * The setter of the position of the start of the bone
     * @param prevJoint The position of the start of the bone
     * @throws BadAttributeValueExpException If the position is null or not of size (4,1)
     */
    @JsonIgnore
    private void setPrevJoint(SimpleMatrix prevJoint) throws BadAttributeValueExpException {
        if(prevJoint == null) throw new BadAttributeValueExpException("The vector as to be not null");
        if(prevJoint.numRows() != 4 || prevJoint.numCols() != 1) throw new BadAttributeValueExpException("The vector as to be of size (4,1)");
        this.prevJoint = MatrixUtils.fromSimpleMatrix(prevJoint);
    }

    /**
     * The setter of the position of the end of the bone
     * @param nextJoint The position of the end of the bone
     * @throws BadAttributeValueExpException If the position is null or not of size (4,1)
     */
    @JsonProperty
    private void setNextJoint(double[] nextJoint) throws BadAttributeValueExpException {
        if(nextJoint == null) throw new BadAttributeValueExpException("The vector as to be not null");
        this.nextJoint = nextJoint;
    }

    /**
     * The setter of the position of the start of the bone
     * @param prevJoint The position of the start of the bone
     * @throws BadAttributeValueExpException If the position is null or not of size (4,1)
     */
    @JsonProperty
    private void setPrevJoint(double[] prevJoint) throws BadAttributeValueExpException {
        if(prevJoint == null) throw new BadAttributeValueExpException("The vector as to be not null");
        this.prevJoint = prevJoint;
    }

    /**
     * The setter of the type of the bone
     * @param type The type of the bone
     * @throws BadAttributeValueExpException If the type is null
     */
    private void setType(Bone.Type type) throws BadAttributeValueExpException {
        if(type == null) throw new BadAttributeValueExpException("The type as to be not null");
        this.type = type;
    }

    /**
     * A method to know if two BoneStructure are equals
     * @param o The Object that we want to compare with
     * @return Return true if they are equals, false otherwise
     */
    @Override
    @JsonIgnore
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoneStructure that = (BoneStructure) o;
        return Objects.equals(nextJoint, that.nextJoint) && Objects.equals(prevJoint, that.prevJoint) && type == that.type;
    }

    /**
     * A method to compare two BoneStructure to know if they are similar
     * @param boneStructure The BoneStructure that we want to compare with
     * @param divergence The divergence that we accept between both BoneStructure
     * @return Return true if they are similar, false otherwise
     */
    @JsonIgnore
    public boolean compare(BoneStructure boneStructure, float divergence) {
        if (boneStructure == null || boneStructure.getType() != this.getType()) return false;

        divergence = Math.abs(divergence);

        SimpleMatrix prev = boneStructure.getPrevJointMatrix();
        for(int i = 0; i < 3; i++) {
            if(Math.abs(prev.get(i) - getPrevJointMatrix().get(i)) > divergence ) return false;
        }

        SimpleMatrix next = boneStructure.getNextJointMatrix();
        for(int i = 0; i < 3; i++) {
            if(Math.abs(next.get(i) - getNextJointMatrix().get(i)) > divergence ) return false;
        }

        return true;
    }

    /**
     * A method to get a BoneStructure with this one but normalized
     * @param normalizer The matrix normalisation we want to apply on it
     * @return The new BoneStructure normalized
     * @throws BadAttributeValueExpException If matrix is null or of an other size than (4,4)
     */
    @JsonIgnore
    public BoneStructure getNormalizedBoneStructure(SimpleMatrix normalizer) throws BadAttributeValueExpException {
        if(normalizer == null) throw new BadAttributeValueExpException("Normalization matrix has to be not null");
        if(normalizer.numRows() != 4 || normalizer.numCols() !=4) throw new BadAttributeValueExpException("Normalization matrix has to be of size (4,4)");

        SimpleMatrix nextNew = normalizer.mult(getNextJointMatrix());
        SimpleMatrix prevNex = normalizer.mult(getPrevJointMatrix());

        return new BoneStructure(getType(), nextNew, prevNex);
    }
}
