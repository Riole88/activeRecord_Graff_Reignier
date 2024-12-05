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

            if(rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                Personne p = new Personne(nom, prenom);
                p.setId(rs.getInt("id"));
                res = p;
            }
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
                Personne p = new Personne(nom, prenom);
                p.setId(rs.getInt("id"));
                res.add(p);
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

    public void save() {
        try {
            Connection connect = DBConnection.getConnection();
            if (this.getId() == -1) {
                String SQLPrep = "INSERT INTO Personne(nom,prenom) VALUES (?,?)";
                PreparedStatement prep = connect.prepareStatement(SQLPrep);
                //prep.setInt(1, 0);
                prep.setString(1, this.getNom());
                prep.setString(2, this.getPrenom());
                prep.executeUpdate();
            }else{
                String SQLPrep = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?";
                PreparedStatement prep = connect.prepareStatement(SQLPrep);
                prep.setString(1, this.getNom());
                prep.setString(2, this.getPrenom());
                prep.setInt(3, this.getId());
                prep.execute();
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

    public void setId(int idt){
        this.id = idt;
    }

    public static void createTable(){
        try{
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "CREATE TABLE `Personne` (" +
                    "  `id` int(11) NOT NULL PRIMARY KEY," +
                    "  `nom` varchar(40) NOT NULL," +
                    "  `prenom` varchar(40) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.execute();

            String SQLPrepAlter = "ALTER TABLE `Personne`" +
                    "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;";
            prep = connect.prepareStatement(SQLPrepAlter);
            prep.execute();
        }catch(SQLException e2){
            throw new RuntimeException(e2);
        }
    }

    public void delete(){
        try {
            Connection connect = DBConnection.getConnection();
            if (this.id != -1) {/*
                //Si la personne est présente d'un un film, cela va la supprimé pour ne pas aavoir de problème avec les clés étrangères
                String SQLPrepFilm = "UPDATE film" +
                        " SET id_rea = NULL" +
                        " WHERE id_rea = ?;";
                PreparedStatement prep1 = connect.prepareStatement(SQLPrepFilm);
                prep1.setString(1,String.valueOf(this.id));
                prep1.execute();
*/
                String SQLPrep = "DELETE FROM Personne WHERE id = ?;";
                PreparedStatement prep = connect.prepareStatement(SQLPrep);
                prep.setString(1, String.valueOf(this.id));
                prep.execute();
                //Changement de l'id de la personne
                this.setId(-1);

            }
        }catch(SQLException e1){
            throw new RuntimeException(e1);
        }
    }

    public static int getIdIncrement(PreparedStatement prep1) throws SQLException{
        ResultSet rs = prep1.getGeneratedKeys();
        int idReturn = -1;
        if(rs.next()){
            idReturn = rs.getInt(1);
        }
        return idReturn;
    }

    public void saveNew() throws SQLException{
        Connection connect = DBConnection.getConnection();
        String insertString = "INSERT INTO Personne(nom,prenom) VALUES(?,?)";
        PreparedStatement prep1 = connect.prepareStatement(insertString);
        prep1.setString(1, "nom");
        prep1.setString(2,"prenom");
        prep1.execute();
    }

}
