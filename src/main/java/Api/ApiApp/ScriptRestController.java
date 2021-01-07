package Api.ApiApp;

import Core.Script.Script;
import Core.StubPersistence.ScriptPersistance;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/script")
public class ScriptRestController {

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

}
