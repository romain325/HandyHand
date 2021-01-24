package Core.StubPersistence;

import java.util.Collection;
import java.util.List;

public interface ISaveDataManager<T extends Object> {

    void save(T object) throws Exception;
    void saveAll(List<T> list) throws Exception;

}
