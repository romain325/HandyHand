package Core.StubPersistence;

import java.util.List;

/**
 * Interface for data Loading
 * @param <T> Object that'll be loaded
 */
public interface ILoadDataManager<T extends Object>  {

    /**
     * Get all the data as a List
     * @return Data as a List
     * @throws Exception Error while fetching data
     */
    List<T> getAll() throws Exception;

    /**
     * Get a data by his name
     * @param name Name of the data
     * @return corresponding Object
     * @throws Exception not found
     */
    T getByName(String name) throws Exception;

    /**
     * Get a data by his id
     * @param Id id of the data
     * @return corresponding Object
     * @throws Exception not found
     */
    T getById(String Id) throws Exception;

}

