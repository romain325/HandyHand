package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Script.Script;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {

    @GetMapping(value = "/helloworld")
    public String test(){
        new MongoConnexion().test().insert(new Script("test", new String[]{} , "test", "test"));
        return "yes";
    }

    @GetMapping(value = "/helloworld2")
    public String test2(){
        return new MongoConnexion().getMongoClient().getDatabase("testDb").getCollection("script").find().toString();
    }

}
