import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Personne {

    private int id;
    private String nom;
    private String prenom;

    Personne(String nom, String prenom){
        this.nom = nom;
        this.prenom = prenom;
        this.id = -1;
    }

    public static ArrayList<Personne> findAll(){
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
                res.add(new Personne(nom,prenom));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

}
