import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.BsonDocument;
import org.bson.Document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Progetto ADM");
        primaryStage.setScene(new Scene(root, 1500, 700));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {



        launch(args);
        

        //Connessione a MongoDB
        MongoConnection connection = new MongoConnection();
        connection.connect();
        BasicDBObject query = new BasicDBObject("tipostrutt","Bed and breakfast");
        FindIterable<Document> iterDoc = connection.getStrutture().find(query);
        MongoCursor cursor = iterDoc.iterator();

        /*while(cursor.hasNext()){
        System.out.println(cursor.next());
        }*/


        //Connessione a PostGIS

        PostGis postConnection = new PostGis();
        postConnection.connect();
        //System.out.println(postConnection.isConnected());
        //postConnection.execute();









    }
}
