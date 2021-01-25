package Api.ApiApp;

import Core.Script.Script;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/script")
public class ScriptRESTController {

    @GetMapping("/all")
    public List<String> allId(){
        List<String> ids = new LinkedList<>();
        try {
            for (Script s: new ScriptPersistance().getAll()) {
                ids.add(s.getId());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all scripts",e);
        }
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No script");
        return ids;
    }

    @GetMapping("/{id}")
    public Script getById(@PathVariable String id){
        try {
            return new ScriptPersistance().getById(id);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting script by id",e);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Script not found !");
    }

    @DeleteMapping("/{id}")
    public boolean deleteScript(@PathVariable String id){
        try{
            ScriptPersistance scriptPersistance = new ScriptPersistance();
            scriptPersistance.remove(scriptPersistance.getById(id));
            return true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deleting script",e);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Script not found !");
    }

    @PostMapping("/add")
    public String addScript(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity

        //Methods in order to get args
        var obj = new Gson().fromJson(data, JsonObject.class);
        List<String> args = new ArrayList<>();
        for (var elem : obj.getAsJsonArray("args")){
            args.add(elem.getAsString());
        }
        String file = obj.get("file").getAsString();
        String execPath = obj.get("execPath").getAsString();

        if(file.isEmpty() || file.isBlank() || execPath.isEmpty() || execPath.isBlank())  throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error while adding the script, bad arguments !");

        Script script = new Script(execPath, args.toArray(new String[0]), file);
        try {
            new ScriptPersistance().getById(script.getId());
        } catch (NameNotFoundException e) {
            try {
                new ScriptPersistance().save(script);
                return script.getId();
            } catch (Exception e1) {
                e.printStackTrace();
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Error while adding the script !", e1);
            }
        }
        return "Error while adding scripts : Name already exist";
    }

    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity

        var objNew = new Gson().fromJson(data, JsonObject.class);
        List<String> argsNew = new ArrayList<>();
        try {
            for (var elem : objNew.getAsJsonArray("args")){
                argsNew.add(elem.getAsString());
            }
        } catch (Exception ignored) {}

        String fileNew;
        try {
            fileNew = objNew.get("file").getAsString();
        } catch (Exception ignored) {
            fileNew = "";
        }

        String execPathNew;
        try {
            execPathNew = objNew.get("execPath").getAsString();
        } catch (Exception ignored) {
            execPathNew = "";
        }

        String idToModify;
        try {
            idToModify = objNew.get("oldId").getAsString();
        } catch (Exception ignored) {
            idToModify = "";
        }

        Script oldScript;
        ScriptPersistance scriptPersistance = new ScriptPersistance();
        try{
            oldScript = scriptPersistance.getById(idToModify);
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while modifying script, script not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Error occurred while getting old script",e);
        }

        if(fileNew.isEmpty() || fileNew.isBlank()) fileNew = oldScript.getFile();

        if(execPathNew.isEmpty() || execPathNew.isBlank()) execPathNew = oldScript.getExecType();

        if(argsNew.isEmpty()) argsNew = Arrays.asList(oldScript.getArgs());

        Script newScript = new Script(execPathNew, argsNew.toArray(new String[0]), fileNew);

        try {
            scriptPersistance.remove(oldScript);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Error occurred while removing script",e);
        }

        try {
            scriptPersistance.getById(newScript.getId());
        } catch (NameNotFoundException e) {
            try {
                scriptPersistance.save(newScript);
                return newScript.getId();
            }catch (Exception e1){
                e.printStackTrace();
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Error while adding the script !", e1);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Error occurred while verifying id script",e);
        }
        return "Error during modifying !";
    }


}
