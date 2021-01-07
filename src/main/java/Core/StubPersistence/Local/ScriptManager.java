package Core.StubPersistence.Local;

import Core.Script.Script;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScriptManager implements JSONManager {
    private final LocalManager localManager = new LocalManager();

    @Override
    public FileManager getParent() {
        return localManager;
    }

    @Override
    public Path getPath() {
        return Paths.get(this.getParent().getPath() + File.separator + "scripts.json");
    }

    @Override
    public void createFile() throws IOException {
        if(!getParent().exists()) getParent().createFile();
        Files.createFile(getPath());
        save(new Gson().toJson(new Script[]{}, Script[].class));
    }

    @Override
    public Script[] getAll() {
        try {
            checkExistence();
            return new Gson().fromJson(new FileReader(getPath().toString()), Script[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
