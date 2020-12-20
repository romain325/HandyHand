package Core.StubPersistence;

import Core.Script.Script;

import javax.naming.NameNotFoundException;
import java.util.Collection;

public interface ILoadDataManager<T extends Object>  {

    Collection<T> getAll();
    Script getByName(String name) throws NameNotFoundException;

}

