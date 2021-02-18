package Core.Gesture.Matrix.Structure;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import org.ejml.simple.SimpleMatrix;

import javax.management.BadAttributeValueExpException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class of the structure of a Finger, to save positions of this one
 */
public class FingerStructure implements Serializable {
    /**
     * The structure for the bone distal
     */
    private BoneStructure distal;
    /**
     * The structure for the bone intermediate
     */
    private BoneStructure intermediate;
    /**
     * The structure for the bone proximal
     */
    private BoneStructure proximal;
    /**
     * The structure for the bone metacarpals
     */
    private BoneStructure metacarpals;
    /**
     * The type the finger
     */
    private Finger.Type type;
    /**
     * The matrix normalizer of the hand of the finger
     */
    private SimpleMatrix normaliser;

    /**
     * A constructor of the class FingerStructure
     * @param finger The finger that we want save information of
     * @throws BadAttributeValueExpException If the finger is null or not valid
     */
    public FingerStructure(Finger finger) throws BadAttributeValueExpException {
        if(finger == null || !finger.isValid()) throw new BadAttributeValueExpException("Finger as to be not null and valid");

        setType(finger.type());
        setDistal(new BoneStructure(finger.bone(Bone.Type.TYPE_DISTAL)));
        setIntermediate(new BoneStructure(finger.bone(Bone.Type.TYPE_INTERMEDIATE)));
        setProximal(new BoneStructure(finger.bone(Bone.Type.TYPE_PROXIMAL)));
        setMetacarpals(new BoneStructure(finger.bone(Bone.Type.TYPE_METACARPAL)));
    }

    /**
     * A constructor of the class FingerStructure
     * @param type The type of the finger
     * @param distal The structure of the bone distal
     * @param intermediate The structure of the bone intermediate
     * @param proximal The structure of the bone proximal
     * @param metacarpals The structure of the bone metacarpals
     * @throws BadAttributeValueExpException If values are null or not of the good type
     */
    public FingerStructure(Finger.Type type, BoneStructure distal, BoneStructure intermediate, BoneStructure proximal, BoneStructure metacarpals) throws BadAttributeValueExpException {
        setType(type);
        setDistal(distal);
        setIntermediate(intermediate);
        setProximal(proximal);
        setMetacarpals(metacarpals);
    }

    /**
     * A method to get all the bones structure of this hand structure
     * @return All the bones structure of this hand structure
     */
    public List<BoneStructure> getBonesStructure() {
        List<BoneStructure> lb = new ArrayList<>();

        lb.add(getDistal());
        lb.add(getIntermediate());
        lb.add(getProximal());
        lb.add(getMetacarpals());

        return lb;
    }

    /**
     * The getter of the type of the finger
     * @return The type of the finger
     */
    public Finger.Type getType() {
        return type;
    }

    /**
     * The getter of the bone distal of the finger
     * @return The BoneStructure of bone distal of the finger
     */
    public BoneStructure getDistal() {
        return distal;
    }

    /**
     * The getter of the bone intermediate of the finger
     * @return The BoneStructure of bone intermediate of the finger
     */
    public BoneStructure getIntermediate() {
        return intermediate;
    }

    /**
     * The getter of the bone proximal of the finger
     * @return The BoneStructure of bone proximal of the finger
     */
    public BoneStructure getProximal() {
        return proximal;
    }

    /**
     * The getter of the bone metacarpals of the finger
     * @return The BoneStructure of bone metacarpals of the finger
     */
    public BoneStructure getMetacarpals() {
        return metacarpals;
    }

    /**
     * The setter of the type of the finger
     * @param type The type of the finger
     */
    private void setType(Finger.Type type) {
        this.type = type;
    }

    /**
     * The setter of the bone distal of the finger
     * @param distal The BoneStructure of bone distal of the finger
     * @throws BadAttributeValueExpException If the BoneStructure is null or not of the good type
     */
    private void setDistal(BoneStructure distal) throws BadAttributeValueExpException {
        if(distal == null) throw new BadAttributeValueExpException("The bone as to be not null");
        if(distal.getType() != Bone.Type.TYPE_DISTAL) throw new BadAttributeValueExpException("The bone as to be of type distal");
        this.distal = distal;
    }

    /**
     * The setter of the bone intermediate of the finger
     * @param intermediate The BoneStructure of bone intermediate of the finger
     * @throws BadAttributeValueExpException If the BoneStructure is null or not of the good type
     */
    private void setIntermediate(BoneStructure intermediate) throws BadAttributeValueExpException {
        if(intermediate == null) throw new BadAttributeValueExpException("The bone as to be not null");
        if(intermediate.getType() != Bone.Type.TYPE_INTERMEDIATE) throw new BadAttributeValueExpException("The bone as to be of type intermediate");
        this.intermediate = intermediate;
    }

    /**
     * The setter of the bone proximal of the finger
     * @param proximal The BoneStructure of bone proximal of the finger
     * @throws BadAttributeValueExpException If the BoneStructure is null or not of the good type
     */
    private void setProximal(BoneStructure proximal) throws BadAttributeValueExpException {
        if(proximal == null) throw new BadAttributeValueExpException("The bone as to be not null");
        if(proximal.getType() != Bone.Type.TYPE_PROXIMAL) throw new BadAttributeValueExpException("The bone as to be of type proximal");
        this.proximal = proximal;
    }

    /**
     * The setter of the bone metacarpals of the finger
     * @param metacarpals The BoneStructure of bone metacarpals of the finger
     * @throws BadAttributeValueExpException If the BoneStructure is null or not of the good type
     */
    private void setMetacarpals(BoneStructure metacarpals) throws BadAttributeValueExpException {
        if(metacarpals == null) throw new BadAttributeValueExpException("The bone as to be not null");
        if(metacarpals.getType() != Bone.Type.TYPE_METACARPAL) throw new BadAttributeValueExpException("The bone as to be of type metacarpals");
        this.metacarpals = metacarpals;
    }

    /**
     * A method to know if two FingerStructure are equals
     * @param o The Object that we want to compare with
     * @return Return true if they are equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FingerStructure that = (FingerStructure) o;
        return Objects.equals(distal, that.distal) && Objects.equals(intermediate, that.intermediate) && Objects.equals(proximal, that.proximal) && Objects.equals(metacarpals, that.metacarpals) && type == that.type;
    }

    /**
     * A method to compare two FingerStructure to know if they are similar
     * @param fingerStructure The FingerStructure that we want to compare with
     * @param divergence The divergence that we accept between both FingerStructure
     * @return Return true if they are similar, false otherwise
     */
    public boolean compare(FingerStructure fingerStructure, float divergence) {
        if (fingerStructure == null || fingerStructure.getType() != this.getType()) return false;

        divergence = Math.abs(divergence);

        if(!this.getDistal().compare(fingerStructure.getDistal(), divergence)) return false;
        if(!this.getIntermediate().compare(fingerStructure.getIntermediate(), divergence)) return false;
        if(!this.getProximal().compare(fingerStructure.getProximal(), divergence)) return false;
        if(!this.getMetacarpals().compare(fingerStructure.getMetacarpals(), divergence)) return false;

        return true;
    }

    /**
     * A method to get a FingerStructure with this one but normalized
     * @param normalizer The matrix normalisation we want to apply on it
     * @return The new FingerStructure normalized
     * @throws BadAttributeValueExpException If matrix is null or of an other size than (4,4)
     */
    public FingerStructure getNormalizedFingerStructure(SimpleMatrix normalizer) throws BadAttributeValueExpException {
        if(normalizer == null) throw new BadAttributeValueExpException("Normalization matrix has to be not null");
        if(normalizer.numRows() != 4 || normalizer.numCols() !=4) throw new BadAttributeValueExpException("Normalization matrix has to be of size (4,4)");

        BoneStructure distalNew = getDistal().getNormalizedBoneStructure(normalizer);
        BoneStructure intermediateNew = getIntermediate().getNormalizedBoneStructure(normalizer);
        BoneStructure proximalNew = getProximal().getNormalizedBoneStructure(normalizer);
        BoneStructure metacarpalsNew = getMetacarpals().getNormalizedBoneStructure(normalizer);

        return new FingerStructure(getType(), distalNew, intermediateNew, proximalNew, metacarpalsNew);
    }
}
