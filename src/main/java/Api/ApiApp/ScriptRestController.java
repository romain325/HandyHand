package Api.ApiApp;

import Core.Script.Script;
import Core.StubPersistence.ScriptPersistance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/script")
public class ScriptRestController {

    @GetMapping("/all")
    public List<String> allId(){
        ScriptPersistance scriptPersistance = new ScriptPersistance();
        List<String> ids = new LinkedList<>();
        List<Script> scripts =  scriptPersistance.getAll();
        for (Script s: scripts) {
            ids.add(s.getId());
        }
        return ids;
    }

}
