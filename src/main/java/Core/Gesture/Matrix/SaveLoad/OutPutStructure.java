package Core.Gesture.Matrix.SaveLoad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class OutPutStructure {
    private final String filePath = System.getProperty("user.home") + File.separator + ".handyhand" + File.separator + "structure";

    public void WriteObjectToFile(Object serObject, String file) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath + File.separator + file));
            objectOut.writeObject(serObject);
            objectOut.close();
            System.out.println("The Object  was succesfully written to the file : " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
