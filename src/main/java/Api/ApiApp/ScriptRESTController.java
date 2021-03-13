package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Daemon.CallLoop;
import Core.Daemon.Daemon;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Interaction.Interaction;
import Core.Listener.GestureListener;
import Core.Listener.MainListener;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import Core.StubPersistence.GesturePersistance;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.lang.Nullable;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Control local script
 */
@RestController
@RequestMapping("/script")
public class ScriptRESTController {
    private final String filePath = System.getProperty("user.home") + File.separator + ".handyhand" + File.separator + "scripts";
    Map<String,Daemon> daemons = new TreeMap<>();

    /**
     * Get all scripts id's
     * @return list of id
     */
    @GetMapping("/allId")
    public List<String> allId(){
        List<String> ids = new LinkedList<>();
        try {
            for (Script s: new ScriptPersistance().getAll()) {
                ids.add(s.getId());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all scripts",e);
        }
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No script");
        return ids;
    }

    /**
     * Get all scripts
     * @return scripts' list
     */
    @GetMapping("/all")
    public List<Map<String,String>> all() {
        try {
            List<Map<String,String>> elems = new ArrayList<>();
            for (var e : new ScriptPersistance().getAll()){
                elems.add(new HashMap<>(){{
                    put("file", e.getFile());
                    put("description", e.getDescription());
                    put("id", e.getId());
                    put("idGesture", e.getIdGesture() == null ? "" : e.getIdGesture());
                    put("status", String.valueOf(false)); // TODO CHANGE WHEN IMPLEMENTATION ALLOW TO
                }});
            }
            return elems;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching scripts");
        }

    }

    /**
     * Get one script informations
     * @param id script id
     * @return script jsonObject
     */
    @GetMapping("/{id}")
    public Script getById(@PathVariable String id){
        try {
            return new ScriptPersistance().getById(id);
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Script with this ID found",e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while getting script by id",e);
        }
    }

    /**
     * Delete a script from his id
     * @param id script id
     * @return True if action succeed
     */
    @DeleteMapping("/{id}")
    public boolean deleteScript(@PathVariable String id){
        try{
            ScriptPersistance scriptPersistance = new ScriptPersistance();
            scriptPersistance.remove(scriptPersistance.getById(id));
            return true;
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Script not found !");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deleting script",e);
        }
    }

    /**
     * Add a new script to local
     * @param req Request
     * @param data { "file": "", "description": "" , "execType": "", "args": [],"idGesture" : "" }
     * @return Snew script ID
     */
    @PostMapping("/add")
    public String addScript(@Nullable HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);
        List<String> args = new ArrayList<>();
        for (var elem : obj.getAsJsonArray("args")){
            args.add(elem.getAsString());
        }

        Script script;
        try{
            script = new Script(obj.get("execType").getAsString(), args.toArray(new String[0]), obj.get("file").getAsString(), (obj.get("description") == null ? "" : obj.get("description").getAsString()),(obj.get("idGesture") == null ? "" : obj.get("idGesture").getAsString()));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error adding: Bad Arguments");
        }

        try {
            new ScriptPersistance().getById(script.getId());
        } catch (NameNotFoundException e) {
            try {
                new ScriptPersistance().save(script);
                return script.getId();
            } catch (Exception e1) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while adding the script !", e1);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error while adding scripts : Name already exist");
    }

    /**
     * Modify an existing script
     * @param req request
     * @param data { "oldId": "", "file": "", "description": "" , "execType": "", "args": [],"idGesture" : "" }
     * @return modified script id
     */
    @PostMapping("/modify")
    public String modifyScript(@Nullable HttpServletRequest req, @RequestBody String data) {
        var objNew = new Gson().fromJson(data, JsonObject.class);
        Script oldScript;
        ScriptPersistance scriptPersistance = new ScriptPersistance();

        try{
            oldScript = scriptPersistance.getById(objNew.get("oldId").getAsString());
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error while modifying script, script not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Error occurred while getting old script",e);
        }

        Map<String, String> elements = new HashMap<>(){{
            put("file",oldScript.getFile());
            put("execType",oldScript.getExecType());
            put("description", oldScript.getDescription());
            put("idGesture", oldScript.getIdGesture());
        }};

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
                elements.get("execType"),
                argsNew.toArray(new String[0]),
                elements.get("file"),
                elements.get("description"),
                elements.get("idGesture"));

        try{
            try {
                scriptPersistance.remove(oldScript);
                scriptPersistance.getById(newScript.getId());
            } catch (NameNotFoundException e) {
                    scriptPersistance.save(newScript);
                    return newScript.getId();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while modifying script : Saving",e);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while modifying script");
    }

    /***
     * Launch the recognition of the gesture to launch the script
     * @param req httpRequest
     * @param data { "scriptId" : ""}
     * @return launch script
     */
    @PostMapping("/launch")
    public String launchScript(HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);

        Script script;
        try{
            script = new ScriptPersistance().getById(obj.get("scriptId").getAsString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The script with the following id has not been found");
        }

        GestureStructure gestureStructure;
        try {
            gestureStructure =  new GesturePersistance().getById(script.getIdGesture());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The gesture associated with the gesture has not been found");
        }

        Map.Entry<String,String> exec;
        try {
            exec =new ExecPersistance().getByName(script.getExecType());
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The interpreter is not defined yet or not exact");
        }

        Map<String,String> map = new HashMap<>();

        map.put("Python",".py");
        map.put("PHP",".php");
        //TODO add all programming languages extensions

        String extension = map.get(exec.getKey());

        checkFile();

        String fileP;
        if ( extension != null ){
            fileP = filePath+"/"+script.getId()+extension;
        }else{
            fileP = filePath+"/"+script.getId();
        }

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
     * @return launch script
     */
    @PostMapping("/stop")
    public String stopScript(HttpServletRequest req, @RequestBody String data) {
        var obj = new Gson().fromJson(data, JsonObject.class);

        Daemon daemon = daemons.remove(obj.get("scriptId").getAsString());
        if (daemon == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The script is not associated or not founded !");
        }

        daemon.interrupt();

        return "The script have been successfully dissociated !";
    }

    @PostMapping("/status")
    public boolean checkStatus(HttpServletRequest req, @RequestBody String data){
        var obj = new Gson().fromJson(data, JsonObject.class);

        return daemons.containsKey(obj.get("scriptId").getAsString());
    }
}
