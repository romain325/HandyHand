package Core.StubPersistence;

import Core.Script.Script;

import javax.naming.NameNotFoundException;
import java.util.Collection;
import java.util.List;

public interface ILoadDataManager<T extends Object>  {

    List<T> getAll() throws Exception;
    T getByName(String name) throws Exception;
    T getById(String Id) throws Exception;

}

