package Core.StubPersistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;

public class LocalManager {
    private Path getAppPath(){
        return Paths.get(System.getProperty("user.home") + File.separator + ".handyhand");
    }
    private Path getExecPath(){
        return Paths.get(getAppPath() + File.separator + "executable.config");
    }
    private Path getScriptPath(){
        return Paths.get(getAppPath() + File.separator + "scripts.json");
    }

    private boolean mainFolderExists(){
        return Files.exists(getAppPath());
    }
    private boolean execFileExists(){
        return Files.exists(getExecPath());
    }
    private boolean scriptsFileExists(){
        return Files.exists(getScriptPath());
    }

    private void createLocalDir() throws IOException {
        Files.createDirectories(getAppPath());
    }
    private void createExecFile() throws IOException {
        Files.createFile(getExecPath());
        var val = JSONObject.toJSONString(new HashMap());
        savetoFile(getExecPath(), val);
    }
    private void createScriptsFile() throws IOException {
        Files.createFile(getScriptPath());
        var val = JSONArray.toJSONString(new LinkedList<>());
        savetoFile(getScriptPath(), val);
    }

    public JSONObject getExecJson() {
        try{
            if(!mainFolderExists()){
                createLocalDir();
            }
            if(!execFileExists()){
                createExecFile();
            }
            return (JSONObject) new JSONParser().parse(new FileReader(getExecPath().toString()));
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public JSONArray getScriptsJson()  {
        try{
            if(!mainFolderExists()){
                createLocalDir();
            }
            if(!scriptsFileExists()){
                createScriptsFile();
            }
            return (JSONArray) new JSONParser().parse(new FileReader(getScriptPath().toString()));
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void saveExec(JSONObject obj){
        try {
            savetoFile(getExecPath(), obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savetoFile(Path path, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(path.toString());
        writer.write(jsonString);
        writer.close();
    }



}
