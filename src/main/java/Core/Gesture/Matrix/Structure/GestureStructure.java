package Core.Gesture.Matrix.Structure;

/**
 * A class that contain a structure representing a gesture, with some information
 */
public class GestureStructure {
    /**
     * The id of the gesture
     */
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
     * The constructor of the class GestureStructure
     * @param id The id of the gesture
     * @param gesture The structure that contains the gesture
     * @param name The name of the gesture
     * @param description The description of the gesture
     */
    public GestureStructure(String id, IDefineStructure gesture, String name, String description) {
        setId(id);
        setGesture(gesture);
        setName(name);
        setDescription(description);
        setIsDoubleHand(gesture instanceof DoubleHandStructure);
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
     * The setter of the id of the gesture
     * @param id The id of the gesture
     */
    private void setId(String id) {
        this.id = id;
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
}
