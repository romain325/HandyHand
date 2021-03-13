package Core.StubPersistence;

import java.util.List;

/**
 * Interface for saving data
 * @param <T> Object's type that'll be saved
 */
public interface ISaveDataManager<T extends Object> {

    /**
     * Save a piece of data
     * @param object data object
     * @throws Exception Error while saving
     */
    void save(T object) throws Exception;

    /**
     * Save all data's
     * @param list list of data
     * @throws Exception Error while saving data
     */
    void saveAll(List<T> list) throws Exception;

}
