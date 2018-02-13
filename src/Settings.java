public class Settings {

    //costanti relative a MONGO DB
    public static final String MONGO_SERVER_IP = "127.0.0.1";
    public static final int MONGO_SERVER_PORT = 27017;
    public static final String MONGO_DB_NAME = "grotteDB";
    public static final String MONGO_COLLECTION_NAME = "grotte";
    public static final String MONGO_COLLECTION_NAME2 = "strutture";

    //costanti relative a POSTGIS; L'user e pwd sono relative alla
    //mia instanza di DB, nel caso dovreste modificarle se sono diversi nei vostri
    public static final String POSTGIS_SERVER_IP = "127.0.0.1";
    public static final int POSTGIS_SERVER_PORT = 5432;
    public static final String POSTGIS_DB_NAME = "sardegnadb";
    public static final String POSTGIS_DB_URL = "jdbc:postgresql://localhost:5432/sardegnadb";
    public static final int MIN_TRACK_SIZE = 100;
    public static final String POSTGIS_DB_USERNAME = "postgres";
    public static final String POSTGIS_DB_PASSWORD = "seriamente93";


}
