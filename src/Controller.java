import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;

import java.sql.*;
import java.util.Arrays;

public class Controller {

    //Dichiarazione oggetti layout
    @FXML
    public Button buttonQuery1;

    @FXML
    public Button buttonQuery2;

    @FXML
    public Button buttonQuery3;

    @FXML
    public Button buttonQuery4;

    @FXML
    public Button buttonQuery5;

    @FXML
    public Button buttonQuery6;

    @FXML
    public Button buttonConnect;

    @FXML
    public TextArea textAreaResult1;

    @FXML
    public TextArea textAreaResult2;


    MongoConnection connection = new MongoConnection();

    Connection conn;

    //Metodi click sui bottoni
    @FXML
    public void buttonQuery1Clicked(){
        if (connection.isConnected()){

            textAreaResult1.setText("");
            BasicDBObject query = new BasicDBObject("tipostrutt","Albergo");
            query.put("istat_com","Sassari");
            query.put("stelle","* * *");
            FindIterable<Document> iterDoc = connection.getStrutture().find(query).projection(new BasicDBObject("nome",true)
                    .append("_id",false)
                    .append("indirizzo",true));
            MongoCursor cursor = iterDoc.iterator();
            while (cursor.hasNext()){
                String ris = cursor.next().toString();
                String risultato = ris.substring(10,ris.length()-2);
                textAreaResult1.setText(textAreaResult1.getText()+risultato+"\n");

            }

        }
        else {
            textAreaResult1.setText("E' necessario connettersi a MongoDB");
        }


    }

    @FXML
    public void buttonQuery2Clicked(){
        if (connection.isConnected()){

            textAreaResult1.setText("");
            BasicDBObject query = new BasicDBObject("COMUNE","URZULEI");
            FindIterable<Document> iterDoc = connection.getGrotte().find(query).projection(new BasicDBObject("NOME",true)
                    .append("_id",false));
            MongoCursor cursor = iterDoc.iterator();
            while (cursor.hasNext()){
                String ris = cursor.next().toString();
                String risultato = ris.substring(10,ris.length()-2);
                textAreaResult1.setText(textAreaResult1.getText()+risultato+"\n");

            }

        }
        else {
            textAreaResult1.setText("E' necessario connettersi a MongoDB");
        }

    }

    @FXML
    public void buttonQuery3Clicked(){



        if (connection.isConnected()){
            textAreaResult1.setText("");
            Document support = new Document( "_id", "$PROVINCIA");
            support.put("NUMERO DI GROTTE", new Document( "$sum", 1));
            Document group = new Document("$group", support);
            Document sort= new Document("$sort", new Document("NUMERO DI GROTTE", -1));
            Document limit = new Document("$limit",2);

            AggregateIterable aggregate = connection.getGrotte().aggregate(Arrays.asList(group,sort,limit));

            MongoCursor cursor = aggregate.iterator();
            while(cursor.hasNext()){
                String ris = cursor.next().toString();
                String risultato = ris.substring(14,ris.length()-2);
                textAreaResult1.setText(textAreaResult1.getText()+"PROVINCIA: "+risultato+"\n");
            }
        }

        else {
            textAreaResult1.setText("E' necessario connettersi a MongoDB");
        }



    }

    @FXML
    public void buttonQuery4Clicked(){

        textAreaResult2.setText("");


        String sql = "SELECT s.nome as nome_struttura,g.nome as nome_grotta,ST_Distance(ST_MakePoint(ST_X(ST_Transform(s.geom,3035)),ST_Y(ST_Transform(s.geom,3035)))::geography,\n" +
                "                                ST_MakePoint(ST_X(ST_Transform(g.geom,3035)),ST_Y(ST_Transform(g.geom,3035)))::geography) /1000 as distanza\n" +
                "from grotte_e_caverne_3 g\n" +
                "join strutture_turistiche_1 s\n" +
                "on g.gid<>s.gid\n" +
                "where g.nome = 'GROTTA DEL BUE MARINO'  and s.tipostrutt='Bed and breakfast'\n" +
                "order by distanza\n" +
                "limit 1;\n";
        try {
            conn = DriverManager.getConnection(Settings.POSTGIS_DB_URL, Settings.POSTGIS_DB_USERNAME, Settings.POSTGIS_DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                textAreaResult2.setText("B&B: "+rs.getString("nome_struttura") + ", Grotta: "+rs.getString("nome_grotta")+", Distanza: " +rs.getString("distanza")+" km");
            }

        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void buttonQuery5Clicked(){
        textAreaResult2.setText("");


        String sql = "SELECT g.nome as nome_grotta\n" +
                "from strutture_turistiche_1 s\n" +
                "join grotte_e_caverne_3 g\n" +
                "on ST_DWithin(ST_MakePoint(ST_X(ST_Transform(s.geom,3035)),ST_Y(ST_Transform(s.geom,3035)))::geography\n" +
                "             ,ST_MakePoint(ST_X(ST_Transform(g.geom,3035)),ST_Y(ST_Transform(g.geom,3035)))::geography,10000)\n" +
                "where s.nome = 'EDERA'";
        try {
            conn = DriverManager.getConnection(Settings.POSTGIS_DB_URL, Settings.POSTGIS_DB_USERNAME, Settings.POSTGIS_DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                textAreaResult2.setText(textAreaResult2.getText()+rs.getString("nome_grotta")+"\n");
            }

        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void buttonQuery6Clicked(){
        textAreaResult2.setText("");


        String sql = "SELECT s.nome as nome_strutt, count(*) as num_grotte\n" +
                "from strutture_turistiche_1 s\n" +
                "join grotte_e_caverne_3 g\n" +
                "on ST_DWithin(ST_MakePoint(ST_X(ST_Transform(s.geom,3035)),ST_Y(ST_Transform(s.geom,3035)))::geography\n" +
                "             ,ST_MakePoint(ST_X(ST_Transform(g.geom,3035)),ST_Y(ST_Transform(g.geom,3035)))::geography,5000)\n" +
                "where s.siglaprov = 'SS' and s.tipostrutt= 'Albergo'\n" +
                "group by s.nome\n" +
                "order by num_grotte desc\n" +
                "LIMIT 1";
        try {
            conn = DriverManager.getConnection(Settings.POSTGIS_DB_URL, Settings.POSTGIS_DB_USERNAME, Settings.POSTGIS_DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                textAreaResult2.setText("Albergo: "+rs.getString("nome_strutt")+" ,numero di grotte: "+rs.getString("num_grotte"));
            }

        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    public void buttonConnectClicked(){
        connection.connect();
        if (connection.isConnected()){
            textAreaResult1.setText("Connessione a MongoDB riuscita");
        }
        else {
            textAreaResult1.setText("E' necessario connettersi a MongoDB");
        }

    }


}

