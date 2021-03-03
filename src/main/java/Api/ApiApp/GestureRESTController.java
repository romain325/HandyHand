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
        if(ids.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No script");
        return ids;
    }

    @GetMapping("/all")
    public List<GestureStructure> all() throws Exception {
        try{
            List<GestureStructure> val = new ArrayList<GestureStructure>(new GesturePersistance().getAll());
            return val;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/{id}")
    public GestureStructure getById(@PathVariable String id){
        try {
            return new GesturePersistance().getById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while getting gesture by id",e);
        }
    }

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

    /*
    * { "name":"", "description": "", "distance": bool, "double": bool  }
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while add gesture",e);
        }
        try{
            new GesturePersistance().getById(structure.getId());
        }catch (Exception e){
            try {
                new GesturePersistance().save(structure);
                return structure.getId();
            }catch (Exception e1){
                e.printStackTrace();
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while adding gesture : id already exist");
    }


    @PostMapping("/modify")
    public String modifyScript(HttpServletRequest req, @RequestBody String data) {
        var objNew = new Gson().fromJson(data, JsonObject.class);

        GestureStructure oldGesture=null;
        try {
            oldGesture = new GesturePersistance().getById(objNew.get("oldId").getAsString());
        } catch (NameNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            new GesturePersistance().remove(oldGesture);
            new GesturePersistance().save(newGesture);
        }catch (Exception e){
            return "Error during saving !";
        }

        return "The gesture have been modified";
    }

}
