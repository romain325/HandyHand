package Testing.StubTest;

import Core.Script.Script;
import Core.StubPersistence.Local.*;
import Core.StubPersistence.ScriptPersistance;
import Testing.Tester;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class StubTesting implements Tester {
    @Override
    public void start() {
        ScriptManager scriptManager = new ScriptManager();
        ExecutableManager executableManager = new ExecutableManager();

        var map2 = executableManager.getAllMap();
        map2.put("rdrfgo","ezefziofhj");
        executableManager.save(new Gson().toJson(map2, Map.class));

    }
}
