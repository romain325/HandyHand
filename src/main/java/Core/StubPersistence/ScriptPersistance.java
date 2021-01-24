package Core.StubPersistence;

import Core.Script.Script;
import Core.StubPersistence.Local.ScriptManager;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.naming.NameNotFoundException;
import java.util.*;

public class ScriptPersistance implements IScriptDataManager{
    ScriptManager scriptManager = new ScriptManager();

    @Override
    public List<Script> getAll() {
        return new LinkedList<>(Arrays.asList(scriptManager.getAll()));
    }

    @Override
    public Script getByName(String name) throws NameNotFoundException {
        for(Script obj : scriptManager.getAll()){
            if(obj.getFile().equals(name))
                return obj;
        }
        throw new NameNotFoundException(name);
    }

    @Override
    public Script getById(String id) throws NameNotFoundException {
        for(Script obj : scriptManager.getAll()){
            if(obj.getId().equals(id)) return obj;
        }
        throw new NameNotFoundException(id);
    }

    @Override
    public void save(Script object) throws Exception {
        List<Script> list = new LinkedList<>(Arrays.asList(scriptManager.getAll()));
        list.add(object);
        try{
            scriptManager.save(new Gson().toJson(list, List.class));
        }catch (Exception e){
            throw new Exception(object.getId());
        }
    }

    @Override
    public void saveAll(List<Script> listScripts) {
        scriptManager.save(new Gson().toJson(listScripts, List.class));
    }

    public void remove(Script script){
        List<Script> list = new LinkedList<>(Arrays.asList(scriptManager.getAll()));
        list.remove(script);
        System.out.println(list.contains(script));
        scriptManager.save(new Gson().toJson(list,List.class));
    }


}
