package Core.StubPersistence;

import java.util.Collection;

public interface ILoadDataManager<T extends Object>  {

    Collection<T> getAll();
    Collection<T> getByName();

}

