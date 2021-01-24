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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/script")
public class ScriptRESTController {

    @GetMapping("/all")
    public List<String> allId(){
        List<String> ids = new LinkedList<>();
        for (Script s: new ScriptPersistance().getAll()) {
            ids.add(s.getId());
        }
        return ids;
        //TODO erreur si pas de scripts
    }

    @GetMapping("/{id}")
    public Script getById(@PathVariable String id){
        try {
            return new ScriptPersistance().getById(id);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
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
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Script not found !");
    }


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
            }catch (Exception e1){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Error while adding the script !", e1);
            }
        }
        return "Error while adding the script !";
    }

    //TODO modifier un script

    /*
     *  @PostMapping("/modify")
     *
     *  {"oldId" : *ancienId* ,"execPath":  "test", "args":  ["-h"], "file":  "john2reaper"}
     *  if null pas de modification
     *
     *  si tu trouve pas : throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "mess");
    *
    * Tu récupére le script,tu créer le nouveau, tu supprime l'ancien et t'ajoute le nouveau
     */

}
