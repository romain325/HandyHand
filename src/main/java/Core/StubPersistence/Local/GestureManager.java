package Core.StubPersistence.Local;

import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Script.Script;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestureManager implements JSONManager {
    private final LocalManager localManager = new LocalManager();

    @Override
    public FileManager getParent() {
        return localManager;
    }

    @Override
    public Path getPath() {
        return Paths.get(this.getParent().getPath() + File.separator + "gesture.json");
    }

    @Override
    public void createFile() throws IOException {
        if(!getParent().exists()) getParent().createFile();
        Files.createFile(getPath());
        save(new Gson().toJson(new GestureStructure[][]{}, GestureStructure[].class));
    }

    @Override
    public GestureStructure[] getAll() {
        try {
            checkExistence();
            return new Gson().fromJson(new FileReader(getPath().toString()), GestureStructure[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}