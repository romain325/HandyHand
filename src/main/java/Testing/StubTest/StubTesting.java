package Testing.StubTest;

import Core.Script.Script;
import Core.StubPersistence.Local.*;
import Testing.Tester;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StubTesting implements Tester {
    @Override
    public void start() {
        JSONManager scriptManager = new ScriptManager();
        JSONArray val = (JSONArray) scriptManager.getAll();

        scriptManager.save(val);

        var obj = new JSONArray();
        obj.add(new Script("pite", new String[]{"-v", "--help"}, "iohger").toJSON());
        System.out.println(obj.toJSONString());
    }
}
