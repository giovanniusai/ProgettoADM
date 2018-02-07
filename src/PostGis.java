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
        String sql = "SELECT * FROM grotte_e_caverne_3";
        try {
            conn = DriverManager.getConnection(Settings.POSTGIS_DB_URL, Settings.POSTGIS_DB_USERNAME, Settings.POSTGIS_DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("nome"));
            }

        } catch (SQLException e ){
            System.out.println(e.getMessage());
        }



    }



}