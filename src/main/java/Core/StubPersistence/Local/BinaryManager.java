package Core.StubPersistence.Local;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Binary Local file manager
 * @param <T> type of the object
 */
public interface BinaryManager<T extends Object> extends FileManager{
    /**
     * get all objects
     * @return List of T
     */
    LinkedList<T> getAll();

    /**
     * Verify the existence of a data file, and init it
     * @throws IOException cant' write
     */
    default void checkExistence() throws IOException {
        if(!exists()){
            createFile();
            save(new LinkedList<T>());
        }
    }

    /**
     * Save a list of object to file
     * @param object list of object to file
     */
    default void save(LinkedList<T> object){
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(getPath().toString()));
            objectOut.writeObject(object);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}