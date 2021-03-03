package Core.StubPersistence.Local;

import Core.Gesture.Matrix.Structure.GestureStructure;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class GestureManager implements BinaryManager<GestureStructure> {
    private final LocalManager localManager = new LocalManager();

    @Override
    public FileManager getParent() {
        return localManager;
    }

    @Override
    public Path getPath() {
        return Paths.get(this.getParent().getPath() + File.separator + "gesture.obj");
    }

    @Override
    public void createFile() throws IOException {
        if(!getParent().exists()) getParent().createFile();
        Files.createFile(getPath());
        save(new LinkedList<GestureStructure>());
    }

    @Override
    public LinkedList<GestureStructure> getAll() {
        try {
            checkExistence();
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(getPath().toString()));
            return (LinkedList<GestureStructure>) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}