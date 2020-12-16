package Testing.StubTest;

import Core.Script.Script;
import Core.StubPersistence.LocalManager;
import Core.StubPersistence.StubPersistance;
import Testing.Tester;

import java.util.Collection;
import java.util.List;

public class StubTesting implements Tester {
    LocalManager localManager = new LocalManager();
    @Override
    public void start() {
        var jsonExec = localManager.getExecJson();
        jsonExec.put("python3","/usr/bin/python3");
        localManager.saveExec(jsonExec);
    }
}
