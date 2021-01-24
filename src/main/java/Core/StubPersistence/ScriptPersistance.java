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
    public List<Script> getAll() throws Exception {
        try {
            return new LinkedList<>(Arrays.asList(scriptManager.getAll()));
        } catch (Exception e) {
            throw new Exception("Error getAll ScriptPersistance");
        }
    }

    @Override
    public Script getByName(String name) throws Exception, NameNotFoundException {
        try{
            for(Script obj : scriptManager.getAll()){
                if(obj.getFile().equals(name))
                    return obj;
            }
            throw new NameNotFoundException(name);
        } catch (Exception e) {
            throw new Exception("Error getByName ScriptPersistance");
        }
    }

    @Override
    public Script getById(String id) throws Exception, NameNotFoundException {
        try{
            for(Script obj : scriptManager.getAll()){
                if(obj.getId().equals(id)) return obj;
            }
            throw new NameNotFoundException(id);
        } catch (Exception e) {
            throw new Exception("Error getById ScriptPersistance");
        }
    }

    @Override
    public void save(Script object) throws Exception {
        try{
            List<Script> list = new LinkedList<>(Arrays.asList(scriptManager.getAll()));
            list.add(object);
            try{
                scriptManager.save(new Gson().toJson(list, List.class));
            }catch (Exception e){
                throw new Exception(object.getId());
            }
        } catch (Exception e) {
            throw new Exception("Error save ScriptPersistance");
        }
    }

    @Override
    public void saveAll(List<Script> listScripts) throws Exception {
        try{
            scriptManager.save(new Gson().toJson(listScripts, List.class));
        } catch (Exception e) {
            throw new Exception("Error saveAll ScriptPersistance");
        }
    }

    public void remove(Script script) throws Exception {
        try{
            List<Script> list = new LinkedList<>(Arrays.asList(scriptManager.getAll()));
            list.remove(script);
            System.out.println(list.contains(script));
            scriptManager.save(new Gson().toJson(list,List.class));
        } catch (Exception e) {
            throw new Exception("Error remove ScriptPersistance");
        }
    }


}
