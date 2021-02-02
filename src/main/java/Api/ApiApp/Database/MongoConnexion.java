package Api.ApiApp.Database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoConnexion {
    private final MongoClient mongoClient = MongoClients.create();

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public @Bean MongoTemplate test(){
        return new MongoTemplate(mongoClient, "testDb");
    }
}
