import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {


    private int id;
    private String nom;
    private String prenom;

    Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }

    public static ArrayList<Personne> findAll() {
        ArrayList<Personne> res = new ArrayList<Personne>();
        try {
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Personne;";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                res.add(new Personne(nom, prenom));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static Personne findById(int idPers) {
        Personne res = null;
        try {
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Personne WHERE id = idPers;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.execute();

            ResultSet rs = prep.getResultSet();

            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            res = new Personne(nom, prenom);
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
        return res;
    }

    public static ArrayList<Personne> findByName(String nom_p){
        ArrayList<Personne> res = new ArrayList<Personne>();
        try{
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Personne WHERE nom = ?";
            PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
            prep1.setString(1, nom_p);
            prep1.execute();
            ResultSet rs = prep1.getResultSet();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                res.add(new Personne(nom, prenom));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String name){
        this.nom = name;
    }

    public static void createTable(){
        try{
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "CREATE TABLE `Personne` (" +
                    "  `id` int(11) NOT NULL," +
                    "  `nom` varchar(40) NOT NULL," +
                    "  `prenom` varchar(40) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
        }catch(SQLException e2){
            throw new RuntimeException(e2);
        }
    }


}
