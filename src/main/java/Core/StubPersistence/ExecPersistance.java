package Core.StubPersistence;

import Core.Script.Script;
import Core.StubPersistence.Local.ExecutableManager;
import com.google.gson.Gson;
import jdk.jshell.spi.ExecutionControl;

import javax.naming.NameNotFoundException;
import java.util.*;

public class ExecPersistance implements IExecDataManager{
    ExecutableManager executableManager = new ExecutableManager();

    @Override
    public void save(Map.Entry<String,String> object) {
        var map = executableManager.getAllMap();
        map.put(object.getKey(),object.getValue());
        executableManager.save(new Gson().toJson(map));
    }

    @Override
    public void saveAll(List<Map.Entry<String,String>> elemList) {
        var map = executableManager.getAllMap();
        for (var entry: elemList) {
            map.put(entry.getKey(), entry.getValue());
        }
        executableManager.save(new Gson().toJson(map));
    }

    public void remove(String id){
        var map = executableManager.getAllMap();
        map.remove(new String(Base64.getDecoder().decode(id.getBytes())));
        executableManager.save(new Gson().toJson(map));
    }

    @Override
    public List<Map.Entry<String,String>> getAll() {
        return Arrays.asList(executableManager.getAll().clone());
    }

    public Map<String,String> getAllAsMap(){
        return executableManager.getAllMap();
    }

    @Override
    public Map.Entry<String,String> getByName(String name) throws NameNotFoundException {
        Map<String, String> values = executableManager.getAllMap();
        if(!values.containsKey(name)){
            throw new NameNotFoundException("Name not found");
        }
        return new AbstractMap.SimpleEntry<String,String>(name, values.get(name));
    }

    @Override
    public Map.Entry<String, String> getById(String id) throws NameNotFoundException {
        for(var obj : executableManager.getAll()){
            if(getId(obj).equals(id)) return obj;
        }
        throw new NameNotFoundException(id);
    }

    public static String getId(Map.Entry<String,String> entry){
        return new String(Base64.getEncoder().encode(entry.getKey().getBytes()));
    }
}
