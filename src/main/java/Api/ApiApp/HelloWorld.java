package Api.ApiApp;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {
    @GetMapping(value = "/helloworld", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(){
        return "ieugh";
    }


}
