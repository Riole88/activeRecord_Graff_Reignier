import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class DBConnection{

    private static Connection connect = null;
    private static String userName = "root";
    private static String password = "";
    private static String serverName = "127.0.0.1";
    private static String portNumber = "3306";
    private static String tableName = "personne";
    private static String dbName = "testpersonne";

    private DBConnection() {
        try {
            // creation de la connection
            Properties properties = new Properties();
            properties.put("user", userName);
            properties.put("password", password);

            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            connect = DriverManager.getConnection(urlDB, properties);
        } catch (SQLException e) {
            System.out.println("*** ERREUR SQL ***");
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection(){
        if(connect == null) {
            new DBConnection();
        }
        return connect;
    }

    public static synchronized void setNomDB(String nomDB){
        dbName = nomDB;
        connect = null;
    }
}
