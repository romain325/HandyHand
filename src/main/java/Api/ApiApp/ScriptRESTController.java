package Api.ApiApp;

import Core.Script.Script;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/script")
public class ScriptRESTController {

    @GetMapping("/allId")
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

    @GetMapping("/all")
    public List<Map<String,String>> all() throws Exception {
        List<Map<String,String>> elems = new ArrayList<>();
        for (var e : new ScriptPersistance().getAll()){
            elems.add(new HashMap<>(){{put("file", e.getFile()); put("description", e.getDescription()); put("id", e.getId());}});
        }
        return elems;
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

        Script script;

        try{
            script = new Script(obj.get("execType").getAsString(), args.toArray(new String[0]), obj.get("file").getAsString(), (obj.get("description") == null ? "" : obj.get("description").getAsString()));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error adding: Bad Arguments");
        }


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
        Script oldScript;
        ScriptPersistance scriptPersistance = new ScriptPersistance();

        try{
            oldScript = scriptPersistance.getById(objNew.get("oldId").getAsString());
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while modifying script, script not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Error occurred while getting old script",e);
        }

        Map<String, String> elements = new HashMap<>(){{
            put("file",oldScript.getFile());
            put("execPath",oldScript.getExecType());
            put("description", oldScript.getDescription());
        }};

        for(var elem : elements.keySet()){
            try{
                elements.put(elem, objNew.get(elem).getAsString());
            }catch (Exception ignored){}
        }

        List<String> argsNew = new ArrayList<>();
        try {
            for (var elem : objNew.getAsJsonArray("args")){
                argsNew.add(elem.getAsString());
            }
        } catch (Exception ignored) {}

        if(argsNew.isEmpty()) argsNew = Arrays.asList(oldScript.getArgs());

        Script newScript = new Script(elements.get("execPath"), argsNew.toArray(new String[0]), elements.get("file"), elements.get("description"));

        try{
            try {
                scriptPersistance.remove(oldScript);
                scriptPersistance.getById(newScript.getId());
            } catch (NameNotFoundException e) {
                    scriptPersistance.save(newScript);
                    return newScript.getId();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Error while saving",e);
        }
        return "Error during modifying !";
    }


}
