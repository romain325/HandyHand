package Testing.StubTest;

import Core.Script.Script;
import Core.StubPersistence.Local.ExecutableManager;
import Core.StubPersistence.Local.ScriptManager;
import Core.StubPersistence.ScriptPersistance;
import Testing.Tester;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StubTesting implements Tester {
    @Override
    public void start() {
        ScriptPersistance scriptPersistance = new ScriptPersistance();
        ScriptManager scriptManager = new ScriptManager();
        ExecutableManager executableManager = new ExecutableManager();

        var map2 = executableManager.getAllMap();
        map2.put("rdrfgo","ezefziofhj");
        executableManager.save(new Gson().toJson(map2, Map.class));

        List<Script> list = new LinkedList<>(Arrays.asList(scriptManager.getAll()));
        list.add(new Script("python3", new String[]{"-h"}, "zkef"));
        scriptManager.save(new Gson().toJson(list, List.class));

    }
}
