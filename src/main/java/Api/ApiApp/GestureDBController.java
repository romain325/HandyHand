package Api.ApiApp;
import Api.ApiApp.Database.MongoConnexion;
import Api.ApiApp.UserDBController;
import Core.Gesture.Matrix.SaveLoad.InPutStructure;
import Core.Gesture.Matrix.Structure.BoneStructure;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.mongodb.DBObject;
import org.ejml.simple.SimpleMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/gestureDB")
public class GestureDBController {

    @GetMapping("/allId")
    public List<String> allId(HttpServletRequest req){
        UserDBController.validAuth(req);

        List<String> ids = new LinkedList<>();
        try {
            for (GestureStructure g: new MongoConnexion().handyDB().findAll(GestureStructure.class)) {
                ids.add(g.getId());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all gesture",e);
        }
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No script");
        return ids;
    }

    @GetMapping("/all")
    public List<DBObject> all(HttpServletRequest req) {
        try{
            UserDBController.validAuth(req);
            //HandStructure gestureStructure =(HandStructure) new InPutStructure().readObjectInFile("testHandStructure");
            //new MongoConnexion().handyDB().save(new GestureStructure("testId2",gestureStructure,"test","test"));
            List<DBObject> val = new ArrayList<>(new MongoConnexion().handyDB().findAll(DBObject.class,"gestureStructure"));
            System.out.println(val.get(0));
            return val;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public GestureStructure getById(HttpServletRequest req, @PathVariable String id){
        UserDBController.validAuth(req);
        try {
            return new MongoConnexion().handyDB().findById(id,GestureStructure.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting script by id",e);
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteGesture(HttpServletRequest req, @PathVariable String id){
        UserDBController.validAuth(req);
        try{
            GestureStructure structure = new MongoConnexion().handyDB().findById(id,GestureStructure.class);
            new MongoConnexion().handyDB().remove(structure);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deleting gesture",e);
        }
    }


    @PostMapping(value = "/add")
    public String add(HttpServletRequest req, @RequestBody String data){
        UserDBController.validAuth(req);

        Frame frame;
        Controller controller;
        try {
            controller= new Controller();
            frame=controller.frame();
            HandStructure structure = new HandStructure(frame.hands().get(0));
        }catch (Exception e){
            return "Error :" + e.getMessage();
        }



        return "ça a marché";
    }

    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        UserDBController.validAuth(req);

        var objNew = new Gson().fromJson(data, JsonObject.class);

        Script oldScript= new MongoConnexion().handyDB().findById(objNew.get("oldId").getAsString(),Script.class);
        Map<String, String> elements = new HashMap<>();
        try {
            elements.put("file", oldScript.getFile());
            elements.put("execPath", oldScript.getExecType());
            elements.put("description", oldScript.getDescription());
        }catch (Exception e){
            return "The id of this script is not registered in our database !";
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

        Script newScript = new Script(elements.get("execPath"), argsNew.toArray(new String[0]), elements.get("file"), elements.get("description"));

        try {
            new MongoConnexion().handyDB().remove(oldScript);
            new MongoConnexion().handyDB().save(newScript);
        }catch (Exception e){
            return "Error during saving !";
        }


        return "The scripts have been modified";
    }

    //TODO ajouter la gestion de l'auteur du script
}
