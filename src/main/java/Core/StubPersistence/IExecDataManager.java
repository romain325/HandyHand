package Core.StubPersistence;

import Core.Script.Script;

import java.util.Map;

public interface IExecDataManager  extends ILoadDataManager<Map.Entry<String,String>>,ISaveDataManager<Map.Entry<String,String>> {
}
