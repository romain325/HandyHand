package Core.StubPersistence;

import java.util.Collection;

public interface ISaveDataManager<T extends Object> {

    void save(T object);
    void saveAll(Collection<T> list);

}
