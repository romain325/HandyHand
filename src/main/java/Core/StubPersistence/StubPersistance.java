package Core.StubPersistence;

import Core.Script.Script;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

public class StubPersistance implements IScriptDataManager{

    public URL filePath;
    JSONParser jsonParser = new JSONParser();

    public StubPersistance(){
        filePath= getClass().getResource("StubExec.json");
    }


    @Override
    public Collection<Script> getAll() {
        try {
            Object obj;
            obj = jsonParser.parse(new FileReader(filePath.toString()));
            JSONArray scripts = (JSONArray) obj;
            scripts.forEach(script -> parseScriptObject((JSONObject)script));
            Collection<Script> collection= new LinkedList<Script>();
            return collection;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Script parseScriptObject(JSONObject script){
        String execObject = (String) script.get("Exec");
        String filePathObject = (String) script.get("FilePath");
        String[] argsObject = (String[]) script.get("Args");
        Script s = new Script(execObject,argsObject,filePathObject);
        return s;
    }

    @Override
    public Collection<Script> getByName() {
        return null;
    }

    @Override
    public void save(Script object) {

    }

    @Override
    public void saveAll(Collection<Script> list) {

    }
}