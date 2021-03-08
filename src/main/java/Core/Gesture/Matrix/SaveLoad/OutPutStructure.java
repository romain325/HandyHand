package Core.Gesture.Matrix.SaveLoad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * A method to write Objects in a file
 */
public class OutPutStructure {
    /**
     * The filepath where we want to write the object
     */
    private final String filePath = System.getProperty("user.home") + File.separator + ".handyhand" + File.separator + "structure";

    /**
     * A method to write an object in a file
     * @param serObject The serializable object that we want to write
     * @param file The name of the file that we want for him
     */
    public void writeObjectToFile(Object serObject, String file) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath + File.separator + file));
            objectOut.writeObject(serObject);
            objectOut.close();
            System.out.println("The Object  was successfully written to the file : " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
