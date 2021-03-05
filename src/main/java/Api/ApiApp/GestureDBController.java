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

/**
 * online gesture rest controller
 */
@RestController
@RequestMapping("/gestureDB")
public class GestureDBController {

    /**
     * get all gestures id
     * @param req HttpRequest auth
     * @return all ids
     */
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
        return ids;
    }

    /**
     * Get all detailled gestures
     * @param req authed Httprequest
     * @return all detailed gestures
     */
    @GetMapping("/all")
    public List<GestureStructure> all(HttpServletRequest req) {
        try{
            UserDBController.validAuth(req);
            return new ArrayList<>(new MongoConnexion().handyDB().findAll(GestureStructure.class,"gestureStructure"));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Get a specific gesture from his id
     * @param req authed http req
     * @param id the Gesture id
     * @return  detailed gestuire
     */
    @GetMapping("/{id}")
    public GestureStructure getById(HttpServletRequest req, @PathVariable String id){
        UserDBController.validAuth(req);
        try {
            return new MongoConnexion().handyDB().findById(id,GestureStructure.class,"gestureStructure");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting script by id: " + e.getMessage(),e);
        }
    }

    /**
     * Delete a gesture from his di
     * @param req authed http req
     * @param id gesture id
     * @return boole
     */
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


    /**
     * Add a new gesture
     * @param req authed http req
     * @param data { "name":"", "description": "", "distance": bool, "double": bool  }
     * @return new script id
     */
    @PostMapping(value = "/add")
    public String add(HttpServletRequest req, @RequestBody String data){
        UserDBController.validAuth(req);

        var objNew = new Gson().fromJson(data, JsonObject.class);

        Frame frame;
        Controller controller;
        GestureStructure gestureStructure = null;
        try {
            controller= new Controller();
            if(!controller.isConnected()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No leap motion connected");
            }
            while(!(controller.frame().isValid())){}
            gestureStructure = new GestureStructure(new HandStructure(controller.frame().hands().get(0)),objNew.get("name").getAsString(),objNew.get("description").getAsString()
                    ,objNew.get("distance").getAsBoolean(),objNew.get("double").getAsBoolean());

            new MongoConnexion().handyDB().insert(gestureStructure,"gestureStructure");

        }catch (BadAttributeValueExpException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Your hand(s) isn't visible by the controller or the controller is disconnected !");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Error: " + e.getMessage());
        }
        return gestureStructure.getId();
    }

    /**
     * Modiify an existing gesture from his id
     * @param req authed http req
     * @param data { "name":"", "description": "", "distance": bool, "double": bool, "oldId": ""  }
     * @return new id
     */
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
            if(!controller.isConnected()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No leap motion connected");
            }
            while(!(controller.frame().isValid())){}
            frame=controller.frame();
            structure = new HandStructure(frame.hands().get(0));
        }catch (BadAttributeValueExpException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Your hand(s) isn't visible by the controller or the controller is disconnected !");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Error: " + e.getMessage());
        }

        Map<String, Object> elements = new HashMap<>();

        try {
            elements.put("name", oldGesture.getName());
            elements.put("description", oldGesture.getDescription());
            elements.put("double", oldGesture.isDoubleHand());
            elements.put("distance", oldGesture.isDistanceImportant());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The id of this script is not registered in our database !");
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,  "Error: " + e.getMessage());
        }
        return newGesture.getId();
    }

}
