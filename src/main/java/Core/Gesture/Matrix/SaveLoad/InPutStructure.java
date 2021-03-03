package Core.Gesture.Matrix.SaveLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * A method to read Objects in a file
 */
public class InPutStructure {
    /**
     * The filepath where we want to read the object
     */
    private final String filePath = System.getProperty("user.home") + File.separator + ".handyhand" + File.separator + "structure";

    /**
     * A method to read an object in a file
     * @param file The name of the file to read
     * @return The object read in the file, or null
     */
    public Object readObjectInFile(String file) {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filePath + File.separator + file));
            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
