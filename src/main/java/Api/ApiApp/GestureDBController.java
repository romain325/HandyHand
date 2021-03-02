package Api.ApiApp;
import Api.ApiApp.Database.MongoConnexion;
import Api.ApiApp.UserDBController;
import Core.Gesture.Matrix.SaveLoad.InPutStructure;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.mongodb.DBObject;
import org.ejml.simple.SimpleMatrix;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.BadAttributeValueExpException;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all gestures",e);
        }
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No script");
        return ids;
    }

    @GetMapping("/all")
    public List<GestureStructure> all(HttpServletRequest req) {
        try{
            UserDBController.validAuth(req);
            List<GestureStructure> val = new ArrayList<>(new MongoConnexion().handyDB().findAll(GestureStructure.class,"gestureStructure"));
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
            return new MongoConnexion().handyDB().findById(id,GestureStructure.class,"gestureStructure");
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

        var objNew = new Gson().fromJson(data, JsonObject.class);

        Frame frame;
        Controller controller;
        try {
            controller= new Controller();
            while(!(controller.frame().isValid())){}
            frame=controller.frame();
            HandStructure structure = new HandStructure(frame.hands().get(0));
            new MongoConnexion().handyDB().insert(new GestureStructure(structure,objNew.get("name").getAsString(),objNew.get("description").getAsString()
                    ,objNew.get("distance").getAsBoolean(),objNew.get("double").getAsBoolean()),"gestureStructure");
        }catch (BadAttributeValueExpException e){
            return "Your hand(s) isn't visible by the controller or the controller is disconnected !";
        } catch (Exception e){
            return "Error : " + e.getMessage();
        }
        return "The gesture have been added !";
    }

    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        UserDBController.validAuth(req);

        var objNew = new Gson().fromJson(data, JsonObject.class);

        GestureStructure oldGesture= new MongoConnexion().handyDB().findById(objNew.get("oldId").getAsString(),GestureStructure.class);

        Frame frame;
        Controller controller;
        HandStructure structure;
        try {
            controller= new Controller();
            while(!(controller.frame().isValid())){}
            frame=controller.frame();
            structure = new HandStructure(frame.hands().get(0));
        }catch (BadAttributeValueExpException e){
            return "Your hand(s) isn't visible by the controller or the controller is disconnected !";
        }catch (Exception e){
            return "Error : " + e.getMessage() + e.getClass();
        }

        Map<String, Object> elements = new HashMap<>();

        try {
            elements.put("name", oldGesture.getName());
            elements.put("description", oldGesture.getDescription());
            elements.put("double", oldGesture.isDoubleHand());
            elements.put("distance", oldGesture.isDistanceImportant());
        }catch (Exception e){
            return "The id of this script is not registered in our database !";
        }

        for(var elem : elements.keySet()){
            try{
                if(elements.get(elem) instanceof Boolean){
                    elements.put(elem, objNew.get(elem).getAsBoolean());
                }else{
                    elements.put(elem, objNew.get(elem).getAsString());
                }
            }catch (Exception ignored){}
        }

        GestureStructure newGesture= new GestureStructure(structure,(String) elements.get("name"),(String) elements.get("description"),(Boolean) elements.get("distance"),(Boolean) elements.get("double"));

        try {
            new MongoConnexion().handyDB().remove(oldGesture);
            new MongoConnexion().handyDB().save(newGesture);
        }catch (Exception e){
            return "Error during saving !";
        }

        return "The gesture have been modified";
    }

}
