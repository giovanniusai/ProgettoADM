import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.BsonDocument;
import org.bson.Document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {


    public static void main(String[] args) throws SQLException {

        Graphic window = new Graphic();

        //Connessione a MongoDB
        /*MongoConnection connection = new MongoConnection();
        connection.connect();
        BasicDBObject query = new BasicDBObject("COMUNE","GONNESA");
        FindIterable<Document> iterDoc = connection.getGrotte().find(query);
        MongoCursor cursor = iterDoc.iterator();*/

        //Connessione a PostGIS

        PostGis postConnection = new PostGis();
        postConnection.connect();
        System.out.println(postConnection.isConnected());
        postConnection.execute();


        //while(cursor.hasNext()){
            //System.out.println(cursor.next());
        //}






    }
}
