package Core.StubPersistence.Local;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

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
