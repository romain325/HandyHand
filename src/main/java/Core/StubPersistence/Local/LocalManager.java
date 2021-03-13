package Core.StubPersistence.Local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Local gesture of the files 
 */
public class LocalManager implements FileManager {
    @Override
    public FileManager getParent() {
        return null;
    }

    @Override
    public Path getPath() {
        return Paths.get(System.getProperty("user.home") + File.separator + ".handyhand");
    }

    @Override
    public void createFile() throws IOException {
        Files.createDirectories(getPath());
    }
}
