import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;





public class MongoConnection {
    private static MongoConnection instance;
    private MongoClient mongoClient;
    private MongoCollection<Document> grotte;
    private MongoCollection<Document> strutture;
    private boolean connected = false;

    public MongoConnection() {

    }


    public void connect() {
        if (!connected) {
            mongoClient = new MongoClient(Settings.MONGO_SERVER_IP, Settings.MONGO_SERVER_PORT);
            MongoDatabase db = mongoClient.getDatabase(Settings.MONGO_DB_NAME);
            grotte = db.getCollection(Settings.MONGO_COLLECTION_NAME);
            strutture = db.getCollection(Settings.MONGO_COLLECTION_NAME2);

            connected = true;
        }
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

    public MongoCollection<Document> getStrutture() {
        return strutture;
    }
}
