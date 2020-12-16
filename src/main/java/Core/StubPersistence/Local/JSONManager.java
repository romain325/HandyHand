package Core.StubPersistence.Local;


import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public interface JSONManager extends FileManager{
    default JSONAware getAll(){
        try {
            if(!exists()) createFile();
            return (JSONAware) new JSONParser().parse(new FileReader(getPath().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    default void save(JSONAware object){
        try {
            savetoFile(getPath(), object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
