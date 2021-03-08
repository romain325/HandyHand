package Api.ApiApp.Database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Connect to MongoDB database
 */
public class MongoConnexion {
    private final MongoClient mongoClient = MongoClients.create();

    /**
     * Get HandyHand DB
     * @return handyHand Db Template
     */
    public @Bean MongoOperations handyDB(){ return new MongoTemplate(mongoClient, "HandyHand"); }
}
