package Api.ApiApp.Database;

import Core.Script.Script;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface ScriptRepo extends MongoRepository<Script, String> {
    List<Script> findByFile(@Param("file") String id);
}
