package Core.StubPersistence.Local;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public interface JSONManager<T extends Object> extends FileManager{
    T[] getAll();
    default void checkExistence() throws IOException {
        if(!exists()) createFile();
    }
    default void save(String object){
        try {
            savetoFile(getPath(), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
