package Core.StubPersistence.Local;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public interface FileManager {
    FileManager getParent();
    Path getPath();
    void createFile() throws IOException;
    default boolean exists(){
        return Files.exists(getPath());
    }
    default void savetoFile(Path path, String val) throws IOException {
        if(!getParent().exists()) getParent().createFile();
        FileWriter writer = new FileWriter(path.toString());
        writer.write(val);
        writer.close();
    }
}
