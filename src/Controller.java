import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;

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


    MongoConnection connection = new MongoConnection();

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

    }

    @FXML
    public void buttonQuery4Clicked(){

    }

    @FXML
    public void buttonQuery5Clicked(){

    }

    @FXML
    public void buttonQuery6Clicked(){

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

