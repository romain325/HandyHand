package Core.StubPersistence;

import Core.Script.Script;
import Core.StubPersistence.Local.ExecutableManager;
import com.google.gson.Gson;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.web.client.HttpClientErrorException;

import javax.naming.NameNotFoundException;
import java.util.*;

public class ExecPersistance implements IExecDataManager{
    ExecutableManager executableManager = new ExecutableManager();

    @Override
    public void save(Map.Entry<String,String> object) throws Exception {
        try {
            var map = executableManager.getAllMap();
            map.put(object.getKey(),object.getValue());
            executableManager.save(new Gson().toJson(map));
        } catch (Exception e) {
            throw new Exception("Error save ExecPersistance");
        }
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

    public Map<String,String> getAllAsMap() throws Exception {
        try {
            return executableManager.getAllMap();
        } catch (Exception e) {
            throw new Exception("Error getAllAsMap ExecPersistance");
        }
    }

    @Override
    public Map.Entry<String,String> getByName(String name) throws Exception, NameNotFoundException {
        try {
            Map<String, String> values = executableManager.getAllMap();
            if(!values.containsKey(name)){
                throw new NameNotFoundException("Name not found");
            }
            return new AbstractMap.SimpleEntry<String,String>(name, values.get(name));
        } catch (Exception e) {
            throw new Exception("Error getByName ExecPersistance");
        }
    }

    @Override
    public Map.Entry<String, String> getById(String id) throws Exception {
        try{
            for(var obj : executableManager.getAll()){
                try{
                    if(getId(obj).equals(id)) return obj;
                } catch (NameNotFoundException ignored) {}
            }
            throw new NameNotFoundException(id);
        } catch (Exception e) {
            throw new Exception("Error getById ExecPersistance");
        }

    }

    public static String getId(Map.Entry<String,String> entry) throws NameNotFoundException {
        try {
            return new String(Base64.getEncoder().encode(entry.getKey().getBytes()));
        } catch (Exception e) {
            throw new NameNotFoundException("Exec not found");
        }
    }
}
