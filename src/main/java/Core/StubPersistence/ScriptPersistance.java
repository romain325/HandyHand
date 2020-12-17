package Core.StubPersistence;

import Core.Script.Script;
import Core.StubPersistence.Local.ScriptManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ScriptPersistance implements IScriptDataManager{
    ScriptManager scriptManager = new ScriptManager();

    @Override
    public Collection<Script> getAll() {
        /*JSONArray scripts = (JSONArray) scriptManager.getAll();
        Collection<Script> collection= new LinkedList<>();
        scripts.forEach(script -> {
            System.out.println(script.getClass());
            collection.add(ScriptManager.scriptFactory((JSONObject) script));
        });
        return collection;*/
        return null;
    }

    @Override
    public Script getByName(String name) {
        /*for(JSONObject obj : (List<JSONObject>) scriptManager.getAll()){
            if(obj.get("file") == name)
                return ScriptManager.scriptFactory(obj);
        }*/
        return null;
    }

    @Override
    public void save(Script object) {

    }

    @Override
    public void saveAll(Collection<Script> list) {

    }
}
