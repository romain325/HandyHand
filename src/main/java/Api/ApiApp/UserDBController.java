package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import Core.User.Token;
import Core.User.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.lang.Nullable;
import netscape.javascript.JSObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.*;

/**
 * Control user informations in DB
 */
@RestController
@RequestMapping("/user")
public class UserDBController {

    /**
     * Create a new user in db
     * @param req req
     * @param data { "mail": "", "password": "" }
     * @return JSONObject with status 200 if done, Error otherwise
     */
    @PostMapping(value = "/add")
    public String add(@Nullable HttpServletRequest req, @RequestBody String data) {
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

        JsonObject returnVal = new JsonObject();
        returnVal.addProperty("status", 200);
        returnVal.addProperty("message", "The user have been added to the DB");
        return returnVal.toString();
    }

    /**
     * associate a script to a user
     * @param req req
     * @param data { "scriptId": "" }
     * @return Success Message
     */
    @PostMapping(value = "/addScript")
    public String addScript(HttpServletRequest req, @RequestBody String data) {
        validAuth(req);

        var obj = new Gson().fromJson(data, JsonObject.class);

        User user;
        try {
            user = new MongoConnexion().handyDB().findById(getConnectedUserId(req),User.class,"user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user is associated with this id !");
        }

        try {
            new MongoConnexion().handyDB().remove(user);
            user.addScript( obj.get("scriptId").getAsString());
            new MongoConnexion().handyDB().save(user);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while associating the Script with the user");
        }


        return "The script have been associated with the user !";
    }

    /**
     * Get users script
     * @param req Request
     * @param id User id
     * @return List of all user's script
     */
    @GetMapping("/{id}")
    public List<Script> allScripts(HttpServletRequest req,@PathVariable String id){
        UserDBController.validAuth(req);

        ScriptDBController scriptDBController= new ScriptDBController();
        List<Script> scripts= new LinkedList<>();
        User user = new MongoConnexion().handyDB().findById(id,User.class);
        List<String> strings = null;
        if (user != null) {
            strings = user.getScriptsId();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user doesnt exist !");
        }

        for (String s : strings ) {
            scripts.add(scriptDBController.getById(req, s));
        }
        return scripts;
    }

    /**
     * Get an access token used to do restricted request
     * @param req Request
     * @param data { "mail": "", "password": "" }
     * @return Connection Token
     */
    @PostMapping(value = "/connect")
    public ResponseEntity<String> connect(@Nullable HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);

        User user,userBD;
        try {
            user = new User(obj.get("mail").getAsString(),obj.get("password").getAsString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email or the password is missing !");
        }

        userBD = new MongoConnexion().handyDB().findById(user.getId(),User.class);
        if(userBD == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is user is not registered in our BD !");

        Token token;
        if(userBD.getPassword().equals(user.getPassword())){
            token = new Token(user.getId());
            try{
                Token tokenfound = new MongoConnexion().handyDB().findById(token.getId(),Token.class,"tokens");
                if(tokenfound != null){
                    new MongoConnexion().handyDB().remove(tokenfound,"tokens");
                }
                new MongoConnexion().handyDB().insert(token,"tokens");
                return new ResponseEntity<>(Base64.getEncoder().encodeToString((token.getId() + ":" + token.getToken()).getBytes()), HttpStatus.OK);
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while trying to refine your token !");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email or the password not valid!");
        }
    }

    /**
     * Remove attached script from user
     * @param req Request
     * @param data { "scriptId": "" }
     * @return Success Message
     */
    @PostMapping(value = "/removeScript")
    public String removeScript(HttpServletRequest req, @RequestBody String data) {
        validAuth(req);
        var obj = new Gson().fromJson(data, JsonObject.class);

        User user;
        try {
            user = new MongoConnexion().handyDB().findById(getConnectedUserId(req),User.class,"user");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user is associated with this id !");
        }
        try {
            new MongoConnexion().handyDB().remove(user);
            String s = obj.get("scriptId").getAsString();
            user.removeScript(s);
            new MongoConnexion().handyDB().save(user);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deassociating script from user");
        }

        return "Success: The script have been disassociated from the user !";
    }

    /**
     * Check user Authorization from String
     * @param authorization user Authorization
     * @return is the auth valid
     */
    static boolean checkAuth(String authorization){
        try {
            String[] authInfos = new String(Base64.getDecoder().decode(authorization)).split(":");
            Token tokenDB = new MongoConnexion().handyDB().findById(authInfos[0],Token.class,"tokens");
            return tokenDB.getToken().equals(authInfos[1]);
        }catch (Exception  e){
            return false;
        }
    }

    /**
     * Check user's Authorization from HttpRequest
     * @param req http request
     * @return is the auth valid
     */
    static boolean checkAuth(HttpServletRequest req){
        return checkAuth(req.getHeader("Authorization"));
    }

    /**
     * Raise Error if Authorization is invalid
     * @param req HttpRequest
     */
    static void validAuth(HttpServletRequest req){
        if(!checkAuth(req)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Auth token aren't valid");
    }

    /**
     * Get Currently connected user from AuthToken
     * @param req httpRequest
     * @return userId
     */
    static String getConnectedUserId(HttpServletRequest req){
        return new String(Base64.getDecoder().decode(req.getHeader("Authorization"))).split(":")[0];
    }
}
