package Api.ApiApp;

import Api.ApiApp.Database.MongoConnexion;
import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Script.Script;
import Core.StubPersistence.ExecPersistance;
import Core.StubPersistence.GesturePersistance;
import Core.StubPersistence.ScriptPersistance;
import Core.User.User;
import Utils.Converter.ExecDTO;
import com.leapmotion.leap.Gesture;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static Api.ApiApp.UserDBController.getConnectedUserId;
import static Api.ApiApp.UserDBController.validAuth;

@RestController
@RequestMapping("/env")
public class ExportEnvController {


    @GetMapping("/{id}")
    public String SyncEnv(HttpServletRequest req, @PathVariable String id){
        validAuth(req);
        try {
            exportGestureLocal(req, id);
            exportGestureToDB(req, id);
            exportScriptLocal(req, id);
            exportScriptToDB(req, id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "The local and distant environment have been sync !";
    }

    private void exportScriptToDB(HttpServletRequest req, String id) throws Exception {
            List<Script> myScriptsLocal = new ScriptPersistance().getAll();
            List<Script> myScriptsDB= new UserDBController().allScripts(req,id);

            Set<Script> set = new LinkedHashSet<>(myScriptsLocal);
            set.addAll(myScriptsDB);
            List<Script> combinedListScript = new LinkedList<>(set);
            List<String> myScriptsId = new LinkedList<>();

            for (Script script: combinedListScript) {
                new MongoConnexion().handyDB().save(script);
                myScriptsId.add(script.getId());
            }

            User user = new MongoConnexion().handyDB().findById(getConnectedUserId(req), User.class,"user");

            List<String> scriptId = user.getScriptsId();

            Set<String> set2 = new LinkedHashSet<String>(myScriptsId);
            set2.addAll(scriptId);
            List<String> combinedListString = new LinkedList<String>(set2);

            new MongoConnexion().handyDB().remove(user);
            user.setScriptsId(combinedListString);
            new MongoConnexion().handyDB().save(user);
    }

    private void exportGestureToDB(HttpServletRequest req, String id) throws Exception {
        List<GestureStructure> myGestureLocal= new GesturePersistance().getAll();


        for (GestureStructure gesture: myGestureLocal) {
            new MongoConnexion().handyDB().save(gesture);
        }
    }

    private void exportGestureLocal(HttpServletRequest req, String id) throws Exception {
        List<Script> scripts = new UserDBController().allScripts(req,id);
        List<String> idGestures = new LinkedList<>();


        for (Script script : scripts) {
            if (!(script.getIdGesture().equals(""))) {
                    idGestures.add(script.getIdGesture());
            }
        }

        if(idGestures.isEmpty()){return;}

        List<GestureStructure> gesturesDB = new LinkedList<>();

        for (String s: idGestures){
            gesturesDB.add(new GestureDBController().getById(req,s));
        }

        List<GestureStructure> gesturesLocal= new GesturePersistance().getAll();


        Set<GestureStructure> set = new LinkedHashSet<>(gesturesLocal);
        set.addAll(gesturesDB);
        System.out.println(set);
        List<GestureStructure> combinedListGesture = new LinkedList<GestureStructure>(set);

        System.out.println(combinedListGesture);


        new GesturePersistance().saveAll(combinedListGesture);

    }

    private void exportScriptLocal(HttpServletRequest req, String id) throws Exception {
        List<Script> myScriptsLocal = new ScriptPersistance().getAll();
        List<Script> myScriptsDB= new UserDBController().allScripts(req,id);

        Set<Script> set = new LinkedHashSet<>(myScriptsLocal);
        set.addAll(myScriptsDB);
        List<Script> combinedListScript = new LinkedList<>(set);
        System.out.println(combinedListScript);

        new ScriptPersistance().saveAll(combinedListScript);
    }

}
