package Api.ApiApp;

import Core.StubPersistence.ExecPersistance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

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
}
