package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Api.ApiApp.Database.ScriptRepo;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/scriptDB")
public class ScriptDBController {

    @GetMapping("/allId")
    public List<String> allId(){
        List<String> ids = new LinkedList<>();
        try {
            for (Script s: new MongoConnexion().handyDB().findAll(Script.class)) {
                ids.add(s.getId());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all scripts",e);
        }
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No script");
        return ids;
    }

    @GetMapping("/all")
    public List<Map<String,String>> all() {
        List<Map<String,String>> elems = new ArrayList<>();
        for (var e : new MongoConnexion().handyDB().findAll(Script.class)){
            elems.add(new HashMap<>(){{put("file", e.getFile()); put("description", e.getDescription()); put("id", e.getId());}});
        }
        return elems;
    }

    @GetMapping("/{id}")
    public Script getById(@PathVariable String id){
        try {
            return new MongoConnexion().handyDB().findById(id,Script.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting script by id",e);
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteScript(@PathVariable String id){
        try{
            Script script = new MongoConnexion().handyDB().findById(id,Script.class);
            new MongoConnexion().handyDB().remove(script);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deleting script",e);
        }
    }


    @PostMapping(value = "/add")
    public String add(HttpServletRequest req, @RequestBody String data){
        var obj = new Gson().fromJson(data, JsonObject.class);
        List<String> args = new ArrayList<>();
        for (var elem : obj.getAsJsonArray("args")){
            args.add(elem.getAsString());
        }

        Script script;

        try{
            script = new Script(obj.get("execPath").getAsString(), args.toArray(new String[0]), obj.get("file").getAsString(), (obj.get("description") == null ? "" : obj.get("description").getAsString()));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error adding: Bad Arguments");
        }

        try {
            new MongoConnexion().handyDB().insert(script);
        }catch (Exception e){
            return "The script is already in our database and his ID is "+script.getId();
        }


        return "The script have been added to the DB";
    }

    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity
        var objNew = new Gson().fromJson(data, JsonObject.class);

        Script oldScript= new MongoConnexion().handyDB().findById(objNew.get("oldId").getAsString(),Script.class);
        Map<String, String> elements = new HashMap<>();
        try {
            elements.put("file", oldScript.getFile());
            elements.put("execPath", oldScript.getExecType());
            elements.put("description", oldScript.getDescription());
        }catch (Exception e){
            return "The id of this script is not registered in our database !";
        }

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

        try {
            new MongoConnexion().handyDB().remove(oldScript);
            new MongoConnexion().handyDB().save(newScript);
        }catch (Exception e){
            return "Error during saving !";
        }


        return "The scripts have been modified";
    }
}
