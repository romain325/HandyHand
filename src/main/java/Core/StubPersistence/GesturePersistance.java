package Core.StubPersistence;

import Core.Gesture.Matrix.Structure.GestureStructure;
import Core.Script.Script;
import Core.StubPersistence.Local.GestureManager;
import Core.StubPersistence.Local.ScriptManager;
import com.google.gson.Gson;

import javax.naming.NameNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GesturePersistance implements IGestureDataManager{
    GestureManager gestureManager = new GestureManager();

    @Override
    public List<GestureStructure> getAll() throws Exception {
        try {
            return new LinkedList<>(Arrays.asList(gestureManager.getAll()));
        } catch (Exception e) {
            throw new Exception("Error getAll GesturePersistance");
        }
    }

    @Override
    public GestureStructure getByName(String name) throws NameNotFoundException {
        for(GestureStructure obj : gestureManager.getAll()){
            if(obj.getName().equals(name))
                return obj;
        }
        throw new NameNotFoundException(name);
    }

    @Override
    public GestureStructure getById(String id) throws NameNotFoundException {
        for(GestureStructure obj : gestureManager.getAll()){
            if(obj.getId().equals(id)) return obj;
        }
        throw new NameNotFoundException(id);
    }

    @Override
    public void save(GestureStructure object) throws Exception {
        List<GestureStructure> list = new LinkedList<>(Arrays.asList(gestureManager.getAll()));
        list.add(object);
        try{
            gestureManager.save(new Gson().toJson(list, List.class));
        }catch (Exception e){
            throw new Exception(object.getId());
        }
    }

    @Override
    public void saveAll(List<GestureStructure> listScripts) throws Exception {
        try{
            gestureManager.save(new Gson().toJson(listScripts, List.class));
        } catch (Exception e) {
            throw new Exception("Error saveAll GesturePersistance");
        }
    }

    public void remove(GestureStructure gestureStructure) throws Exception {
        try{
            List<GestureStructure> list = new LinkedList<>(Arrays.asList(gestureManager.getAll()));
            list.remove(gestureStructure);
            System.out.println(list.contains(gestureStructure));
            gestureManager.save(new Gson().toJson(list,List.class));
        } catch (Exception e) {
            throw new Exception("Error remove GesturePersistance");
        }
    }


}
