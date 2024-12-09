import java.sql.*;
import java.util.ArrayList;

public class Film {
    private int id;
    private String titre;
    private int id_real;

    public Film(String titre, Personne p){
        this.id = -1;
        this.titre = titre;
        this.id_real = p.getId();
    }

    private Film(int id, String titre, int id_rea){
        this.id = id;
        this.titre = titre;
        this.id_real = id_rea;
    }

    public static void createTable(){
        try {
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "CREATE TABLE `Film` (" +
                    "  `id` int(11) NOT NULL PRIMARY KEY," +
                    "  `titre` varchar(40) NOT NULL," +
                    "  `id_rea` int(11) DEFAULT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.execute();

            String SQLPrepAlter = "ALTER TABLE `Film`" +
                    "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;";
            prep = connect.prepareStatement(SQLPrepAlter);
            prep.execute();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void deleteTable(){
        try{
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "DROP TABLE IF EXISTS Film";
            PreparedStatement stmt = connect.prepareStatement(SQLPrep);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Film findById(int idFilm){
        Film res = null;
        try{
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "SELECT * FROM Film WHERE id = ?";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.setInt(1, idFilm);
            prep.execute();

            ResultSet rest = prep.getResultSet();

            if(rest.next()) {
                String titre = rest.getString("titre");
                int idRea = rest.getInt("id_rea");
                Film f = new Film(idFilm, titre, idRea);
                res = f;
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }

        return res;
    }

    public Personne getRealisateur(){
        Personne rea = null;
        Film f = Film.findById(this.getId());
        rea = Personne.findById(f.getId_real());

        return rea;
    }

    public static ArrayList<Film> findByRealisateur(Personne p){
        ArrayList<Film> res = new ArrayList<>();
        try{
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Film WHERE id_rea = ?;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setString(1, String.valueOf(p.getId()));
        prep.execute();

        ResultSet rest = prep.getResultSet();

        while(rest.next()){
            res.add(new Film(rest.getInt("id"), rest.getString("titre"), rest.getInt("id_rea")));
        }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public void delete(){
        try {
            Connection connect = DBConnection.getConnection();
            String SQLPrep = "DELETE FROM Film WHERE id = ?;";
            PreparedStatement prep = connect.prepareStatement(SQLPrep);
            prep.setString(1, String.valueOf(this.id));
            prep.executeUpdate();
            //Changement de l'id de la personne
            this.setId(-1);
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void save(){
        if(this.getId_real() == -1) throw new RealisateurAbsentException();
        try{
            Connection connect = DBConnection.getConnection();
            if(this.getId() == -1){
                String SQLPrep = "INSERT INTO Film(titre,id_rea) VALUES (?,?);";
                PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
                prep.setString(1, this.getTitre());
                prep.setInt(2,this.getId_real());
                prep.executeUpdate();

                ResultSet rs = prep.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    this.setId(id);
                }
            }
            else{
                String SQLPrep = "UPDATE Film SET titre = ?, id_rea = ? WHERE id = ?";
                PreparedStatement prep = connect.prepareStatement(SQLPrep);
                prep.setString(1, this.getTitre());
                prep.setInt(2,this.getId_real());
                prep.setInt(3,this.getId());
                prep.execute();
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public int getId_real() {
        return id_real;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String toString(){
        String res = "Titre : " + this.titre + ", IdFilm : " + this.id + "\n";
        Personne p = getRealisateur();
        res += "Réalisateur : " + p.toString();
        return res;
    }
}
