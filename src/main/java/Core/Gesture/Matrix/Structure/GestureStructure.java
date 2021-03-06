package Core.Gesture.Matrix.Structure;


import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Base64;
import java.util.Objects;

/**
 * A class that contain a structure representing a gesture, with some information
 */
public class GestureStructure implements Serializable {
    /**
     * The id of the gesture
     */
    @Id
    private String id;
    /**
     * The structure that contains the gesture
     */
    private IDefineStructure gesture;
    /**
     * The name of the gesture
     */
    private String name;
    /**
     * The description of the gesture
     */
    private String description;
    /**
     * If the structure is a DoubleHandStructure or not
     */
    private boolean isDoubleHand;
    /**
     * If the distance between both hands are important, so the comparison will include it
     */
    private boolean isDistanceImportant = false;


    /**
     * A constructor of the class GestureStructure
     * @param gesture The structure that contains the gesture
     * @param name The name of the gesture
     * @param description The description of the gesture
     */
    public GestureStructure(IDefineStructure gesture, String name, String description) {
        setGesture(gesture);
        setName(name);
        setDescription(description);
        setIsDoubleHand(gesture instanceof DoubleHandStructure);
        setId();
    }

    /**
     * A constructor of the class GestureStructure
     * @param gesture The structure that contains the gesture
     * @param name The name of the gesture
     * @param description The description of the gesture
     * @param isDistanceImportant If the distance between both hands are important, so the comparison will include it
     */
    public GestureStructure(IDefineStructure gesture, String name, String description, boolean isDistanceImportant , boolean isDoubleHand) {
        setGesture(gesture);
        setName(name);
        setDescription(description);
        setIsDoubleHand(gesture instanceof DoubleHandStructure);
        setDistanceImportant(isDistanceImportant);
        setIsDoubleHand(isDoubleHand);
        setId();
    }

    /**
     * A constructor of the class GestureStructure. This one do nothing
     */
    public GestureStructure(){

    }

    /**
     * The getter of the id of the gesture
     * @return The id of the gesture
     */
    public String getId() {
        return id;
    }

    /**
     * The getter of the structure that contains the gesture
     * @return The structure that contains the gesture
     */
    public IDefineStructure getGesture() {
        return gesture;
    }

    /**
     * The getter of the name of the gesture
     * @return The name of the gesture
     */
    public String getName() {
        return name;
    }

    /**
     * The getter of the description of the gesture
     * @return The description of the gesture
     */
    public String getDescription() {
        return description;
    }

    /**
     * To know if the structure is a DoubleHandStructure or not
     * @return True if the structure is a DoubleHandStructure, false otherwise
     */
    public boolean isDoubleHand() {
        return isDoubleHand;
    }

    /**
     * To know if the distance between both hands are important, so the comparison will include it
     * @return True if the distance between both hands are important, false otherwise
     */
    public boolean isDistanceImportant() {
        return isDistanceImportant;
    }

    /**
     * The setter of the id of the gesture
     */
    private void setId(){
        id = new String(Base64.getEncoder().encode((name+description).toLowerCase().getBytes()));
    }

    /**
     * The setter of the structure that contains the gesture
     * @param gesture The structure that contains the gesture
     */
    private void setGesture(IDefineStructure gesture) {
        this.gesture = gesture;
    }

    /**
     * The setter of the name of the gesture
     * @param name The name of the gesture
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * The setter of the description of the gesture
     * @param description The description of the gesture
     */
    private void setDescription(String description) {
        this.description = description;
    }

    /**
     * To know if the structure is a DoubleHandStructure or not
     * @param doubleHand True if the structure is a DoubleHandStructure, false otherwise
     */
    private void setIsDoubleHand(boolean doubleHand) {
        isDoubleHand = doubleHand;
    }

    /**
     * To know if the distance between both hands are important, so the comparison will include it
     * @param distanceImportant True if the distance between both hands are important, false otherwise
     */
    private void setDistanceImportant(boolean distanceImportant) {
        isDistanceImportant = distanceImportant;
    }

    /**
     * A method to compare gestures within two GestureStructure
     * @param gestureStructure The GestureStructure we want to compare with the current one
     * @param divergence The divergence that we accept between both IDefineStructure in both GestureStructure
     * @return Return true if they are similar, false otherwise
     */
    public boolean compareGestures(GestureStructure gestureStructure, float divergence) {
        if(!gestureStructure.isDoubleHand() && !this.isDoubleHand()) {
            return new StructureManager().compareWithNormalization(gestureStructure.getGesture(), this.getGesture(), divergence);
        }
        else if(gestureStructure.isDoubleHand() && this.isDoubleHand()) {
            if(isDistanceImportant) {
                return new StructureManager().compareWithNormalization(gestureStructure.getGesture(), this.getGesture(), divergence);
            } else {
                return new StructureManager().compareWithNormalizationWithoutDistance(gestureStructure.getGesture(), this.getGesture(), divergence);
            }
        }
        return false;
    }

    /**
     * The setter of the the boolean DoubleHand to know if the Structure is a DoubleHandStructure
     * @param doubleHand The boolean DoubleHand to know if the Structure is a DoubleHandStructure
     */
    public void setDoubleHand(boolean doubleHand) {
        isDoubleHand = doubleHand;
    }

    /**
     * The setter of the id of the GestureStructure
     * @param id The id of the GestureStructure
     */
    public void setId(String id) { this.id = id;}

    /**
     * A method to know if two GestureStructure are the same
     * @param o The Object that we want to compare with the current one
     * @return True if both GestureStructure have the same id
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestureStructure that = (GestureStructure) o;
        return id.equals(that.id);
    }

    /**
     * The method to get the hash code of the object
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
