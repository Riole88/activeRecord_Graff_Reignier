import java.sql.*;
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
            String SQLPrep = "SELECT * FROM Personne WHERE id = ?";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.setInt(1, idPers);
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

    public static void deleteTable(){
        try{
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "DROP TABLE IF EXISTS Personne";
            PreparedStatement stmt = connect.prepareStatement(SQLPrep);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Personne p) {
        try {
            Connection connect = DBConnection.getConnection();
            if (p.getId() == -1) {
                String SQLPrep = "INSERT INTO Personne VALUES (?,?,?)";
                PreparedStatement prep = connect.prepareStatement(SQLPrep);
                prep.setInt(1, 0);
                prep.setString(2, p.getNom());
                prep.setString(3, p.getPrenom());
                prep.executeUpdate();
            }else{
                String SQLPrep = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?";
                PreparedStatement prep = connect.prepareStatement(SQLPrep);
                prep.setString(1, p.getNom());
                prep.setString(2, p.getPrenom());
                prep.setInt(3, p.getId());
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
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
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `nom` varchar(40) NOT NULL," +
                    "  `prenom` varchar(40) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
        }catch(SQLException e2){
            throw new RuntimeException(e2);
        }
    }


}
