package Api.ApiApp;

import Core.Script.Script;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

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
    }

    @GetMapping("/{id}")
    public Script getById(@PathVariable String id){
        try {
            return new ScriptPersistance().getById(id);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteScript(@PathVariable String id){
        try{
            ScriptPersistance scriptPersistance = new ScriptPersistance();
            scriptPersistance.remove(scriptPersistance.getById(id));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/add")
    public boolean addScript(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity

        var obj = new Gson().fromJson(data, JsonObject.class);
        List<String> args = new ArrayList<>();
        for (var elem : obj.getAsJsonArray("args")){
            args.add(elem.getAsString());
        }
        String file = obj.get("file").getAsString();
        String execPath = obj.get("execPath").getAsString();

        if(file.isEmpty() || file.isBlank() || execPath.isEmpty() || execPath.isBlank()) return false;

        Script script = new Script(execPath, args.toArray(new String[0]), file);
        try {
            new ScriptPersistance().getById(script.getId());
        } catch (NameNotFoundException e) {
            new ScriptPersistance().save(script);
            return true;
        }

        return false;
    }

}
