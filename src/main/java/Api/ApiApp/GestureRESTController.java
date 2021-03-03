package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Gesture.Matrix.Structure.HandStructure;
import Core.Script.Script;
import Core.StubPersistence.GesturePersistance;
import Core.StubPersistence.ScriptPersistance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.mongodb.DBObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Gesture REST Controller
 */
@RestController
@RequestMapping("/gesture")
public class GestureRESTController {

    /**
     * Get all gestures ids
     * @return Get all gesture ids
     */
    @GetMapping("/allId")
    public List<String> allId(){
        List<String> ids = new LinkedList<>();
        try {
            for (GestureStructure g: new GesturePersistance().getAll()) {
                ids.add(g.getId());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting all gestures",e);
        }
        return ids;
    }

    /**
     * Get all gestures
     * @return detailed gesture list
     */
    @GetMapping("/all")
    public List<GestureStructure> all() throws Exception {
        try{
            return new ArrayList<>(new GesturePersistance().getAll());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * get a gesture by id
     * @param id selected gesture's id
     * @return selected gesture
     */
    @GetMapping("/{id}")
    public GestureStructure getById(@PathVariable String id){
        try {
            return new GesturePersistance().getById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting gesture by id",e);
        }
    }

    /**
     * Delete a gesture from his id
     * @param id gesture id
     * @return Boolean
     */
    @DeleteMapping("/{id}")
    public boolean deleteGesure(@PathVariable String id){
        try{
            GestureStructure structure = new GesturePersistance().getById(id);
            new GesturePersistance().remove(structure);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while deleting gesture",e);
        }
    }

    /**
     * Add a new gesture
     * @param req Httprequest
     * @param data { "name":"", "description": "", "distance": bool, "double": bool  }
     * @return id of the new gesture
     */
    @PostMapping("/add")
    public String add(HttpServletRequest req, @RequestBody String data) {
        var objNew = new Gson().fromJson(data, JsonObject.class);

        GestureStructure structure;
        Frame frame;
        Controller controller;
        try{
            controller= new Controller();
            while (!(controller.frame().isValid()));
            frame=controller.frame();
            HandStructure hand = new HandStructure(frame.hands().get(0));
            structure = new GestureStructure(hand,objNew.get("name").getAsString(),objNew.get("description").getAsString(),objNew.get("distance").getAsBoolean(),objNew.get("double").getAsBoolean());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while add gesture: " + e.getMessage() ,e);
        }
        try{
            new GesturePersistance().getById(structure.getId());
        }catch (Exception e){
            try {
                new GesturePersistance().save(structure);
                return structure.getId();
            }catch (Exception e1){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while adding gesture : " + e.getMessage());
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while adding gesture : id already exist");
    }

    /**
     * Modify an existing gesture from his id
     * @param req HttpRequest
     * @param data { "name":"", "description": "", "distance": bool, "double": bool  }
     * @return new gesture id
     */
    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        var objNew = new Gson().fromJson(data, JsonObject.class);

        GestureStructure oldGesture=null;
        try {
            oldGesture = new GesturePersistance().getById(objNew.get("oldId").getAsString());
        } catch (NameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Id not found");
        }

        Frame frame;
        Controller controller;
        HandStructure structure = null;
        try {
            controller= new Controller();
            while (!(controller.frame().isValid()));
            frame=controller.frame();
            structure = new HandStructure(frame.hands().get(0));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: while fetching the hand movement");
        }

        Map<String, Object> elements = new HashMap<>();

        try {
            elements.put("name", oldGesture.getName());
            elements.put("description", oldGesture.getDescription());
            elements.put("double", oldGesture.isDoubleHand());
            elements.put("distance", oldGesture.isDistanceImportant());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Id not in the database");
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
            new GesturePersistance().remove(oldGesture);
            new GesturePersistance().save(newGesture);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving");
        }
        return newGesture.getId();
    }

}
