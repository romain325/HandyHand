package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Script.Script;
import Core.User.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserDBController {

    @PostMapping(value = "/add")
    public String add(HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);

        User user;
        try {
            user = new User(obj.get("mail").getAsString(), obj.get("password").getAsString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error adding: Email or password missing !");
        }

        try {
            new MongoConnexion().handyDB().insert(user);
        } catch (Exception e) {
            return "The user is already in our database and his ID is " + user.getId();
        }

        return "The user have been added to the DB";
    }


    @PostMapping(value = "/addScript")
    public String addScript(HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);

        User user;
        try {
            user = new MongoConnexion().handyDB().findById(obj.get("userId").getAsString(),User.class,"user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user is associated with this id !");
        }
        try {
            new MongoConnexion().handyDB().remove(user);
            String s = obj.get("scriptId").getAsString();

            user.addScript(s);

            new MongoConnexion().handyDB().save(user);
        }catch (Exception e){
            return e.getMessage();
        }


        return "The script have been associated with the user !";
    }

    @GetMapping("/{id}")
    public List<Script> allScripts(@PathVariable String id){
        ScriptDBController scriptDBController= new ScriptDBController();
        List<Script> scripts= new LinkedList<>();
        User user = new MongoConnexion().handyDB().findById(id,User.class);
        List<String> strings = null;
        if (user != null) {
            strings = user.getScriptsId();
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesnt exist !");
        }

        for (String s : strings ) {
            scripts.add(scriptDBController.getById(s));
        }
        if (scripts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user doesnt have scripts yes !");
        }

        return scripts;
    }

    //TODO Delete,Update
}
