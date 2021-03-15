package Core.StubPersistence;

import Core.StubPersistence.Local.ExecutableManager;
import com.google.gson.Gson;

import javax.naming.NameNotFoundException;
import java.util.*;

/**
 * Persistence of the executable
 */
public class ExecPersistance implements IExecDataManager{
    ExecutableManager executableManager = new ExecutableManager();

    @Override
    public void save(Map.Entry<String,String> object) throws Exception {
        Map<String, String> map;
        try {
            map = executableManager.getAllMap();
        } catch (Exception e) {
            map = new HashMap<String, String>();
        }
        map.put(object.getKey(),object.getValue());
        executableManager.save(new Gson().toJson(map));
    }

    @Override
    public void saveAll(List<Map.Entry<String,String>> elemList) throws Exception {
        try {
            var map = executableManager.getAllMap();
            for (var entry : elemList) {
                map.put(entry.getKey(), entry.getValue());
            }
            executableManager.save(new Gson().toJson(map));
        } catch (Exception e) {
            throw new Exception("Error saveAll ExecPersistance");
        }
    }

    /**
     * Method to remove an exec from local storage
     * @param id id of the exec to remove
     * @throws Exception if error
     */
    public void remove(String id) throws Exception {
        try {
            var map = executableManager.getAllMap();
            map.remove(new String(Base64.getDecoder().decode(id.getBytes())));
            executableManager.save(new Gson().toJson(map));
        } catch (Exception e) {
            throw new Exception("Error remove ExecPersistance");
        }
    }

    @Override
    public List<Map.Entry<String,String>> getAll() throws Exception {
        try {
            return Arrays.asList(executableManager.getAll().clone());
        } catch (Exception e) {
            throw new Exception("Error getAll ExecPersistance");
        }
    }

    /**
     * Method to get all exec as a map
     * @return exec in a map
     * @throws Exception if error
     */
    public Map<String,String> getAllAsMap() throws Exception {
        try {
            return executableManager.getAllMap();
        } catch (Exception e) {
            throw new Exception("Error getAllAsMap ExecPersistance");
        }
    }

    @Override
    public Map.Entry<String,String> getByName(String name) throws NameNotFoundException {
        Map<String, String> values;
        try {
            values = executableManager.getAllMap();
        } catch (Exception e) {
            throw new NameNotFoundException("Name not found");
        }

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

    /**
     * Method to get the id from an exec
     * @param entry exec with <"type","path">
     * @return the id from the exec
     */
    public static String getId(Map.Entry<String,String> entry) {
        return new String(Base64.getEncoder().encode(entry.getKey().getBytes()));
    }
}
