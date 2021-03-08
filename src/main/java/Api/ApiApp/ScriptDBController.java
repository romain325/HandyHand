package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Daemon.CallLoop;
import Core.Daemon.Daemon;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Gesture.Matrix.Structure.IDefineStructure;
import Core.Interaction.Interaction;
import Core.Listener.GestureListener;
import Core.Listener.MainListener;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Control Db scripts
 */
@RestController
@RequestMapping("/scriptDB")
public class ScriptDBController {
    Map<String,Daemon> daemons = new TreeMap<>();
    private final String filePath = System.getProperty("user.home") + File.separator + ".handyhand" + File.separator + "scripts";

    /**
     * Get all db Scripts' id
     * @param req httpRequest
     * @return ids' list
     */
    @GetMapping("/allId")
    public List<String> allId(HttpServletRequest req){
        UserDBController.validAuth(req);

        List<String> ids = new LinkedList<>();
        try {
            for (Script s: new MongoConnexion().handyDB().findAll(Script.class)) {
                ids.add(s.getId());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all scripts",e);
        }
        return ids;
    }

    /**
     * Get all db Scripts'
     * @param req httpRequest
     * @return scripts' list
     */
    @GetMapping("/all")
    public List<Map<String,String>> all(HttpServletRequest req) {
        UserDBController.validAuth(req);
        try{
            List<Map<String,String>> elems = new ArrayList<>();
            for (var e : new MongoConnexion().handyDB().findAll(Script.class)){
                JsonObject scriptId = new JsonObject();
                scriptId.addProperty("scriptId", e.getId());

                elems.add(new HashMap<>(){{
                    put("file", e.getFile());
                    put("description", e.getDescription());
                    put("id", e.getId());
                    put("idGesture", e.getIdGesture());
                    put("status", String.valueOf(checkStatus(req, scriptId.toString())));
                }});
            }
            return elems;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching scripts");
        }
    }

    /**
     * Get a script by his id
     * @param req Httprequest
     * @param id script id
     * @return Script jsonObject
     */
    @GetMapping("/{id}")
    public Script getById(HttpServletRequest req, @PathVariable String id){
        UserDBController.validAuth(req);

        try {
            Script script = new MongoConnexion().handyDB().findById(id, Script.class);
            if (script == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Script found with this id");

            return script;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting script by id",e);
        }
    }

    /**
     * Delete a script given his id
     * @param req httpRequest
     * @param id script id
     * @return true if operation succeed
     */
    @DeleteMapping("/{id}")
    public boolean deleteScript(HttpServletRequest req, @PathVariable String id){
        UserDBController.validAuth(req);
        try{
            MongoOperations mongo = new MongoConnexion().handyDB();
            mongo.remove(mongo.findById(id,Script.class));
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deleting script, id may be invalid",e);
        }
    }


    /**
     * Add a new script to DB
     * @param req HttpRequest
     * @param data {"args":  ["-h"], "file":  "john2reaper", "description":  "An Amazing script !!", "execType":  "test","idGesture" : "}
     * @return script id
     */
    @PostMapping(value = "/add")
    public String add(HttpServletRequest req, @RequestBody String data){
        UserDBController.validAuth(req);
        var obj = new Gson().fromJson(data, JsonObject.class);

        List<String> args = new ArrayList<>();
        Script script;

        try{
            for (var elem : obj.getAsJsonArray("args")){
                args.add(elem.getAsString());
            }
            script = new Script(obj.get("execType").getAsString(), args.toArray(new String[0]), obj.get("file").getAsString(), (obj.get("description") == null ? "" : obj.get("description").getAsString()),(obj.get("idGesture") == null ? "" : obj.get("idGesture").getAsString()));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error adding: Bad Arguments");
        }

        try {
            new MongoConnexion().handyDB().insert(script);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The script is already in our database and his ID is " + script.getId());
        }

        return script.getId();
    }

    /**
     * Modify an existing script given is id
     * @param req httpRequest
     * @param data { "oldId": "", "args":  [], "file":  "", "description":  "", "execType":  ""}
     * @return modified Script id
     */
    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        UserDBController.validAuth(req);

        var objNew = new Gson().fromJson(data, JsonObject.class);

        Script oldScript;
        Map<String, String> elements = new HashMap<>();
        try {
            oldScript = new MongoConnexion().handyDB().findById(objNew.get("oldId").getAsString(),Script.class);
            elements.put("file", oldScript.getFile());
            elements.put("execPath", oldScript.getExecType());
            elements.put("idGesture", oldScript.getIdGesture());
            elements.put("description", oldScript.getDescription());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The script with the following id has not been found");
        }

        for(var elem : elements.keySet()){
            try{
                elements.put(elem, objNew.get(elem).getAsString());
            }catch (Exception ignored){}
        }

        List<String> argsNew = new ArrayList<>();
        try {
            for (var elem : objNew.getAsJsonArray("args")){
                argsNew.add(elem.getAsString());
            }
        } catch (Exception ignored) {}

        if(argsNew.isEmpty()) argsNew = Arrays.asList(oldScript.getArgs());

        Script newScript = new Script(
                elements.get("execPath"),
                argsNew.toArray(new String[0]),
                elements.get("file"),
                elements.get("description"),
                elements.get("idGesture"));

        try {
            new MongoConnexion().handyDB().remove(oldScript);
            new MongoConnexion().handyDB().save(newScript);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the modification");
        }

        return newScript.getId();
    }

    /***
     * Launch the recognition of the gesture to launch the script
     * @param req httpRequest
     * @param data { "scriptId" : ""}
     * @return launch script
     */
    @PostMapping("/launch")
    public String launchScript(HttpServletRequest req, @RequestBody String data) {
        UserDBController.validAuth(req);
        var obj = new Gson().fromJson(data, JsonObject.class);

        Script script;
        try{
            script = new MongoConnexion().handyDB().findById(obj.get("scriptId").getAsString(),Script.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The script with the following id has not been found");
        }

        GestureStructure gestureStructure;
        try {
            gestureStructure =  new MongoConnexion().handyDB().findById(script.getIdGesture(),GestureStructure.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The gesture associated with the gesture has not been found");
        }

        Map.Entry<String,String> exec = null;
        try {
            System.out.println(script.getExecType());
            System.out.println(new ExecPersistance().getAll());
            exec = new ExecPersistance().getByName(script.getExecType());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The interpreter is not defined yet or not exact");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,String> map = new HashMap<>();

        map.put("Python",".py");
        map.put("PHP",".php");
        //TODO add all programming languages extensions

        String extension = map.get(exec.getKey());

        checkFile();

        String fileP = filePath+"/"+script.getId()+extension;
        File file = new File(fileP);

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            writer.write(script.getFileDecoded());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while transcribing on local files");
        }

        Interaction interaction = new Interaction();
        MainListener listener = new GestureListener(gestureStructure.getGesture());
        interaction.addListener(new MainListener[]{listener}, new Script(exec.getValue(), script.getArgs() ,fileP));
        Daemon daemon = new Daemon(script.getId(), new CallLoop(interaction));
        System.out.println(daemon);
        daemons.put(daemon.getDaemonName(),daemon);

        //TODO remplacer liste de démon par démon unique
        daemon.start();

        return "The script have been successfully associated with the gesture and can now be launch by executing the gesture !";
    }

    private void checkFile() {
        if(!Files.exists(Path.of(filePath))){
           File dir =new File(filePath);
           dir.mkdirs();
        }
    }

    /***
     * Stop the recognition of the gesture to launch the script
     * @param req httpRequest
     * @param data { "scriptId" : ""}
     * @return stop script
     */
    @PostMapping("/stop")
    public String stopScript(HttpServletRequest req, @RequestBody String data) {
        UserDBController.validAuth(req);
        var obj = new Gson().fromJson(data, JsonObject.class);

        Daemon daemon=daemons.remove(obj.get("scriptId").getAsString());
        if (daemon == null){
            return "The script is not associated or not founded !";
        }
        daemon.stop();

        return "The script have been successfully dissociated !";
    }

    /***
     * Check the status of a script
     * @param req httpRequest
     * @param data { "scriptId" : ""}
     * @return status of the script
     */
    @PostMapping("/status")
    public boolean checkStatus(HttpServletRequest req, @RequestBody String data){
        UserDBController.validAuth(req);

        var obj = new Gson().fromJson(data, JsonObject.class);

        return daemons.containsKey(obj.get("scriptId").getAsString());
    }

}
