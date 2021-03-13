package Core.StubPersistence.Local;


import java.io.IOException;

/**
 * JSON Manager for T class
 * @param <T> type of the object
 */
public interface JSONManager<T extends Object> extends FileManager {
    /**
     * Get all value in an array of T
     * @return array of T
     */
    T[] getAll();

    /**
     * Verify existence of a file
     * @throws IOException can't write file
     */
    default void checkExistence() throws IOException {
        if(!exists()) createFile();
    }

    /**
     * Save a JSON String object to a file
     * @param object String version of the JSONObject
     */
    default void save(String object){
        try {
            savetoFile(getPath(), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
