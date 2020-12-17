package Core.StubPersistence;

import Core.Script.Script;

import java.util.Collection;

public interface ILoadDataManager<T extends Object>  {

    Collection<T> getAll();
    Script getByName(String name);

}

