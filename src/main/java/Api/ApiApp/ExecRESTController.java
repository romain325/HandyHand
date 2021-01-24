package Api.ApiApp;

import Core.StubPersistence.ExecPersistance;
import org.springframework.web.bind.annotation.*;
import javax.naming.NameNotFoundException;
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
}
