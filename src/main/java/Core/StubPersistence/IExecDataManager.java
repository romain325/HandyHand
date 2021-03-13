package Core.StubPersistence;

import java.util.Map;

/**
 * Interface for the executables
 */
public interface IExecDataManager  extends ILoadDataManager<Map.Entry<String,String>>,ISaveDataManager<Map.Entry<String,String>> {
}
