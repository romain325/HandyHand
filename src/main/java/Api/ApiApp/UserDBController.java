package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import Core.User.Token;
import Core.User.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserDBController {

    /*
    {
        "mail": "",
        "password": ""
    }
     */
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user is already in our database and his ID is " + user.getId());
        }

        return "{\"status\":\"200\",\"message\":\"The user have been added to the DB\"}";
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

    /*
    Faire ma requête vers api avec requête et mdp
    Si ok on créer token pour utilisateur et on renvoie
    On sauvegarde un hashmap avec association clé token value id utilisateur

     */

    @PostMapping(value = "/connect")
    public String connect(HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);

        User user,userBD;
        try {
            user = new User(obj.get("mail").getAsString(),obj.get("passwd").getAsString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email or the password is missing !");
        }
        try {
            userBD=new MongoConnexion().handyDB().findById(user.getId(),User.class);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is user is not registered in our BD !");
        }
        Token token;
        if(userBD.getPassword().equals(user.getPassword())){
            token = new Token(user.getId());
            try{
                Token tokenfound=new MongoConnexion().handyDB().findById(token.getId(),Token.class,"tokens");
                if(tokenfound!=null){
                    System.out.println(tokenfound.getId()+tokenfound.getToken());
                    new MongoConnexion().handyDB().remove(tokenfound,"tokens");
                    new MongoConnexion().handyDB().insert(token,"tokens");
                    return token.getToken();
                }
            }catch (Exception e){
                return "Error while trying to refine your token !";
            }
            new MongoConnexion().handyDB().insert(token,"tokens");
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email or the password not valid!");
        }

        return token.getToken();
    }


    @PostMapping(value = "/removeScript")
    public String removeScript(HttpServletRequest req, @RequestBody String data) {
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
            user.removeScript(s);
            new MongoConnexion().handyDB().save(user);
        }catch (Exception e){
            return e.getMessage();
        }

        return "The script have been disassociated from the user !";
    }

    private boolean checkAuth(String id,String token){
        Token tokenDB;
        try {
            tokenDB = new MongoConnexion().handyDB().findById(id,Token.class,"tokens");
        }catch (Exception  e){
            return false;
        }
        return tokenDB.getToken().equals(token);
    }
}
