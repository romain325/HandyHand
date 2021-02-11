package Core.Gesture.Matrix.SaveLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class InPutStructure {
    private final String filePath = System.getProperty("user.home") + File.separator + ".handyhand" + File.separator + "structure";

    public Object ReadObjectInFile(String file) {
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
