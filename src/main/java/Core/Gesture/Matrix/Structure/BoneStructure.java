package Core.Gesture.Matrix.Structure;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Vector;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;
import java.io.Serializable;

/**
 * The class of the structure of a bone, to save positions of this one
 */
public class BoneStructure implements Serializable {
    /**
     * The position of the end of the bone
     */
    private SimpleMatrix nextJoint;
    /**
     * The position of the start of the bone
     */
    private SimpleMatrix prevJoint;
    /**
     * The type of the bone
     */
    private Bone.Type type;

    /**
     *
     * @param bone The bone that we want to save informations
     * @throws BadAttributeValueExpException If the bone is null or not valid
     */
    public BoneStructure(Bone bone) throws BadAttributeValueExpException {
        if(bone == null || !bone.isValid()) throw new BadAttributeValueExpException("Bone as to be not null and valid");

        Vector nextJoint = bone.nextJoint();
        SimpleMatrix next = new SimpleMatrix(4, 1);
        next.set(0,0, nextJoint.getX());
        next.set(1,0, nextJoint.getY());
        next.set(2,0, nextJoint.getZ());
        next.set(3,0, 1);

        Vector prevJoint = bone.nextJoint();
        SimpleMatrix prev = new SimpleMatrix(4, 1);
        next.set(0,0, prevJoint.getX());
        next.set(1,0, prevJoint.getY());
        next.set(2,0, prevJoint.getZ());
        next.set(3,0, 1);

        setType(bone.type());
        setNextJoint(next);
        setPrevJoint(prev);
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
    public SimpleMatrix getNextJoint() {
        return nextJoint;
    }

    /**
     * The getter of the position of the start of the bone
     * @return The position of the start of the bone
     */
    public SimpleMatrix getPrevJoint() {
        return prevJoint;
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
    private void setNextJoint(SimpleMatrix nextJoint) throws BadAttributeValueExpException {
        if(nextJoint == null) throw new BadAttributeValueExpException("The vector as to be not null");
        if(nextJoint.numRows() != 4 || nextJoint.numCols() != 1) throw new BadAttributeValueExpException("The vector as to be of size (4,1)");
        this.nextJoint = nextJoint;
    }

    /**
     * The setter of the position of the start of the bone
     * @param prevJoint The position of the start of the bone
     * @throws BadAttributeValueExpException If the position is null or not of size (4,1)
     */
    private void setPrevJoint(SimpleMatrix prevJoint) throws BadAttributeValueExpException {
        if(prevJoint == null) throw new BadAttributeValueExpException("The vector as to be not null");
        if(prevJoint.numRows() != 4 || prevJoint.numCols() != 1) throw new BadAttributeValueExpException("The vector as to be of size (4,1)");
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
}
