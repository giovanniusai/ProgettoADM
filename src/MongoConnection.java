import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;



public class MongoConnection {
    private static MongoConnection instance;
    private MongoClient mongoClient;
    private MongoCollection<Document> grotte;
    private boolean connected = false;

    public MongoConnection() {

    }

    public static MongoConnection getInstance() {
        if (instance == null) {
            instance = new MongoConnection();
        }

        return instance;
    }

    public void connect() {
        if (!connected) {
            mongoClient = new MongoClient(Settings.MONGO_SERVER_IP, Settings.MONGO_SERVER_PORT);
            MongoDatabase db = mongoClient.getDatabase(Settings.MONGO_DB_NAME);
            grotte = db.getCollection(Settings.MONGO_COLLECTION_NAME);

            connected = true;
        }
    }

    public long getCount(){
        return grotte.count();
    }


    public void disconnect() {
        if (connected) {
            mongoClient.close();
            connected = false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public MongoCollection<Document> getGrotte() {
        return grotte;
    }
}
