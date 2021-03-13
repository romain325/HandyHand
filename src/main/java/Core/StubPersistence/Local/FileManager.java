package Core.StubPersistence.Local;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Local file manager
 */
public interface FileManager {
    /**
     * Get parent folder manager
     * @return Parent of the current fileManager
     */
    FileManager getParent();

    /**
     * Get Current file Path
     * @return path of the file/folder
     */
    Path getPath();

    /**
     * Create a file in the context
     * @throws IOException write' access
     */
    void createFile() throws IOException;

    /**
     * Check file existence
     * @return does the file exists
     */
    default boolean exists(){
        return Files.exists(getPath());
    }

    /**
     * Save a value to file
     * @param path Path of the file
     * @param val String rpz of the data
     * @throws IOException write access
     */
    default void savetoFile(Path path, String val) throws IOException {
        if(!getParent().exists()) getParent().createFile();
        FileWriter writer = new FileWriter(path.toString());
        writer.write(val);
        writer.close();
    }
}
