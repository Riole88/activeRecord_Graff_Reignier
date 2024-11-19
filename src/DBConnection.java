import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class DBConnection{

    private static Connection connect;
    private String urlDB;

    private Properties properties;

    public DBConnection(){
        // variables de connection
        String userName = "root";
        String password = "";
        String serverName = "127.0.0.1";
        String portNumber = "3306"; // Port pour XAMP
        //String portNumber = "8889"; // Port par défaut sur MAMP
        String tableName = "personne";
        // il faut une base nommee testPersonne !
        String dbName = "testpersonne";
        try {
            // chargement du driver jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");

            // creation de la connection
            properties = new Properties();
            properties.put("user", userName);
            properties.put("password", password);
            urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;


        }catch (ClassNotFoundException e) {
            System.out.println("*** ERREUR lors du chargement du driver ***");
            e.printStackTrace();
        }
    }

    Connection getConnection(){
        if(connect== null) {
            try {
                connect = DriverManager.getConnection(urlDB, properties);
            } catch (SQLException e) {
                System.out.println("*** ERREUR SQL ***");
                e.printStackTrace();
            }
        }
        return connect;
    }
}
