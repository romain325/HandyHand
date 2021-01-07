package Api.ApiApp;

import Core.StubPersistence.ExecPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exec")
public class ExecRESTController {

    @GetMapping("/all")
    public List<String> getAllId(){
        List<String> ids = new LinkedList<>();
        for (var val: new ExecPersistance().getAll()) {
            ids.add(new String(Base64.getEncoder().encode(val.getKey().getBytes())));
        }
        return ids;
    }

    @GetMapping("/{id}")
    public Map.Entry<String, String> getById(@PathVariable String id){
        try {
            return new ExecPersistance().getById(id);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteScript(@PathVariable String id){
        ExecPersistance execPersistance = new ExecPersistance();
        execPersistance.remove(id);
    }

    @PostMapping("/add")
    public boolean addExec(HttpServletRequest req, @RequestBody String data) {
        //TODO Verify Identity

        var obj = new Gson().fromJson(data, JsonObject.class);
        String file = obj.get("name").getAsString();
        String execPath = obj.get("path").getAsString();

        if(file.isEmpty() || file.isBlank() || execPath.isEmpty() || execPath.isBlank()) return false;

        Map.Entry<String,String> entry = Map.entry(file,execPath);

        new ExecPersistance().save(entry);

        return true;
    }
}
