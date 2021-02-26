package Api.ApiApp;

import Core.StubPersistence.ExecPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Control Local data about the executable
 */
@RestController
@RequestMapping("/exec")
public class ExecRESTController {

    /**
     * Get all executable id
     * @return list of all execs ids
     */
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

    /**
     * Get an executable from his id
     * @param id exec id
     * @return exec informations
     */
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

    /**
     * Delete a given executable
     * @param id exec id
     */
    @DeleteMapping("/{id}")
    public void deleteExec(@PathVariable String id){
        ExecPersistance execPersistance = new ExecPersistance();
        try {
            execPersistance.remove(id);
        }  catch (NameNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Script not found !", e);
        }  catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while removing exec",e);
        }
    }

    /**
     * Create a new executable/path association
     * @param req request
     * @param data { "name": "", "execPath": "" }
     * @return id of the new Script
     */
    @PostMapping("/add")
    public String addExec(@Nullable HttpServletRequest req, @RequestBody String data) {
        var objNew = new Gson().fromJson(data, JsonObject.class);
        Map.Entry<String, String> newExec;
        try {
            newExec = new AbstractMap.SimpleEntry<>(objNew.get("name").getAsString(), objNew.get("execPath").getAsString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while adding Exec, invalid arguments");
        }

        ExecPersistance execPersistance = new ExecPersistance();
        try {
            execPersistance.getByName(newExec.getKey());
        } catch (NameNotFoundException e) {
            try {
                execPersistance.save(newExec);
                return ExecPersistance.getId(newExec);
            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while adding Exec : during saving");
            }
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Error while adding Exec: Exec already existing");
    }

    /**
     * Modify an existing script
     * @param req request
     * @param data { "name": "", "execPath": "" }
     * @return id of the modified script
     */
    @PostMapping("/modify")
    public String modifyExec(@Nullable HttpServletRequest req, @RequestBody String data) {
        var objNew = new Gson().fromJson(data, JsonObject.class);
        String name, newPath;
        try {
            name = objNew.get("name").getAsString();
            newPath = objNew.get("execPath").getAsString();
        } catch (Exception ignored) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error while modifying Exec, bad arguments");
        }

        ExecPersistance execPersistance = new ExecPersistance();
        Map.Entry<String, String> oldExec;
        try {
            oldExec = execPersistance.getByName(name);
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Script not found",e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying Exec : Error occurred while getting old exec",e);
        }

        Map.Entry<String, String> newExec = new AbstractMap.SimpleEntry<>(oldExec.getKey(), newPath);
        try {
            execPersistance.remove(oldExec.getKey());
            execPersistance.save(newExec);
            return ExecPersistance.getId(newExec);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying Exec",e);
        }
    }

}
