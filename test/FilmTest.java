import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {
    Personne p1 = new Personne("Spielberg", "Steven");
    Personne p2 = new Personne("Scott", "Ridley");
    Personne p3 = new Personne("Kubrick", "Stanley");
    Personne p4 = new Personne("Spielberg", "George");

    Film f1 = new Film("Arche perdue", p1);
    Film f2 = new Film("Alien", p2);
    Film f3 = new Film("Temple Maudit", p1);
    Film f4 = new Film("Blade Runner", p2);
    Film f5 = new Film("Alien3", p4);
    Film f6 = new Film("Fight Club", p4);
    Film f7 = new Film("Orange Mecanique", p3);



    @BeforeEach
    /**
     * prepare la base de donnees
     */
    public void creerDonneesTable() throws SQLException {
        Personne.createTable();
        Film.createTable();

        p1.save();
        p2.save();
        p3.save();
        p4.save();


        f1.save();
        f2.save();
        f3.save();
        f4.save();
        f5.save();
        f6.save();
        f7.save();

    }

    @AfterEach
    public void supprimerDonnees() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }



    @Test
    public void testConstructeur() {
        Personne p5 = new Personne("LeTest", "Moi");
        p5.save();
        Film f = new Film("Je Suis Un Test", p5);
        assertEquals(f.getId(), -1,"objet pas dans la base");
    }

    @Test
    public void testGetRealisateur(){
        Personne p6 = new Personne("LeTest2", "Aucun");
        p6.save();
        Film f = new Film("Je Test la méthode getRealisateur", p6);
        f.save();
        assertEquals(p6.getId(),f.getRealisateur().getId());
        assertEquals(p6.getNom(),f.getRealisateur().getNom());
        assertEquals(p6.getPrenom(),f.getRealisateur().getPrenom());
    }

    @Test
    public void testFindByRealisateur(){
        ArrayList<Film> res = f1.findByRealisateur(p1);
        assertEquals(res.get(0).getId(),1);
        assertEquals(res.get(1).getId(),2);
    }
}
