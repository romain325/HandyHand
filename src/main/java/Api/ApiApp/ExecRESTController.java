package Api.ApiApp;

import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyStore;
import java.util.*;

@RestController
@RequestMapping("/exec")
public class ExecRESTController {

    @GetMapping("/all")
    public List<String> getAllId(){
        List<String> ids = new LinkedList<>();
        try {
            for (var val: new ExecPersistance().getAll()) {
                ids.add(new String(Base64.getEncoder().encode(val.getKey().getBytes())));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all execs",e);
        }
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No exec");
        return ids;
    }

    @GetMapping("/{id}")
    public Map.Entry<String, String> getById(@PathVariable String id){
        try {
            return new ExecPersistance().getById(id);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Script not found !",e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting exec",e);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteExec(@PathVariable String id){
        ExecPersistance execPersistance = new ExecPersistance();
        try {
            execPersistance.remove(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while removing exec",e);
        }
    }

    @PostMapping("/add")
    public String addExec(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity

        var objNew = new Gson().fromJson(data, JsonObject.class);
        String name;
        try {
            name = objNew.get("name").getAsString();
        } catch (Exception ignored) {
            name = "";
        }

        String path;
        try {
            path = objNew.get("execPath").getAsString();
        } catch (Exception ignored) {
            path = "";
        }

        if(name.isBlank() || name.isEmpty() || path.isEmpty() || path.isBlank()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error while adding Exec, bad arguments");


        Map.Entry<String, String> newExec = new AbstractMap.SimpleEntry<String, String>(name, path);

        ExecPersistance execPersistance = new ExecPersistance();
        try {
            execPersistance.getByName(name);
        } catch (NameNotFoundException e) {
            try {
                execPersistance.save(newExec);
                return newExec.getKey();
            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while adding Exec : during saving");
            }
        }
        return "Error while adding exec !";
    }

    @PostMapping("/modify")
    public void modifyExec(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity

        var objNew = new Gson().fromJson(data, JsonObject.class);
        String name;
        try {
            name = objNew.get("name").getAsString();
        } catch (Exception ignored) {
            name = "";
        }

        String newPath;
        try {
            newPath = objNew.get("execPath").getAsString();
        } catch (Exception ignored) {
            newPath = "";
        }

        if(name.isBlank() || name.isEmpty() || newPath.isEmpty() || newPath.isBlank()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error while modifying Exec, bad arguments");

        ExecPersistance execPersistance = new ExecPersistance();
        Map.Entry<String, String> oldExec;
        try {
            oldExec = execPersistance.getByName(name);
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Script not found",e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying Exec : Error occurred while getting old exec",e);
        }

        Map.Entry<String, String> newExec = new AbstractMap.SimpleEntry<String, String>(oldExec.getKey(), newPath);

        try {
            execPersistance.remove(oldExec.getKey());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying Exec : Error occurred while removing old exec",e);
        }

        try {
            execPersistance.save(newExec);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying Exec : Error occurred while saving new exec",e);
        }
    }

}
