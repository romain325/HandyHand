package Core.StubPersistence;

import Core.Script.Script;
import Core.StubPersistence.Local.ScriptManager;
import com.google.gson.Gson;

import javax.naming.NameNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Script Managing
 */
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
    public void saveAll(List<Script> listScripts) throws Exception {
        try{
            scriptManager.save(new Gson().toJson(listScripts, List.class));
        } catch (Exception e) {
            throw new Exception("Error saveAll ScriptPersistance");
        }
    }

    /**
     * Remove a piece of data from the saved ones
     * @param script script to remove
     * @throws Exception error while saving
     */
    public void remove(Script script) throws Exception {
        try{
            List<Script> list = new LinkedList<>(Arrays.asList(scriptManager.getAll()));
            list.remove(script);
            scriptManager.save(new Gson().toJson(list,List.class));
        } catch (Exception e) {
            throw new Exception("Error remove ScriptPersistance");
        }
    }


}
