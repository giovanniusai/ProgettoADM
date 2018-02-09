import com.mongodb.client.MongoCursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.List;



public class PostGis {
    private static PostGis instance;
    private java.sql.Connection  conn;
    private boolean isConnected = false;

    public PostGis()  {
    }

    public static PostGis getInstance() {

        if (instance == null) {
            instance = new PostGis();
        }

        return instance;
    }

    /*
     * metodo connect
     */
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(Settings.POSTGIS_DB_URL, Settings.POSTGIS_DB_USERNAME, Settings.POSTGIS_DB_PASSWORD);

            ((org.postgresql.PGConnection) conn).addDataType("geometry", (Class<? extends PGobject>) Class.forName("org.postgis.PGgeometry"));
            ((org.postgresql.PGConnection) conn).addDataType("box3d", (Class<? extends PGobject>) Class.forName("org.postgis.PGbox3d"));
            this.isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void disconnect() {
        if (isConnected) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void execute() {
        String sql = "SELECT s.nome as nome_struttura,g.nome as nome_grotta\n" +
                "from strutture_turistiche_1 s\n" +
                "join grotte_e_caverne_3 g\n" +
                "on ST_DWithin(ST_MakePoint(ST_X(ST_Transform(s.geom,3035)),ST_Y(ST_Transform(s.geom,3035)))::geography\n" +
                "             ,ST_MakePoint(ST_X(ST_Transform(g.geom,3035)),ST_Y(ST_Transform(g.geom,3035)))::geography,1000)\n" +
                "order by s.nome";
        try {
            conn = DriverManager.getConnection(Settings.POSTGIS_DB_URL, Settings.POSTGIS_DB_USERNAME, Settings.POSTGIS_DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println("Grotta: "+rs.getString("nome_grotta")+" Struttura: " + rs.getString("nome_struttura"));
            }

        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }



    }



}