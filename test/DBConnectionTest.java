import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void connection_multiple(){
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();

        assertEquals(c1, c2);
    }

    @Test
    void type(){
        Connection c1 = DBConnection.getConnection();

        assertInstanceOf(java.sql.Connection.class, c1);
    }

    @Test
    void change_bd() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        String create = "CREATE TABLE Test(ID INTEGER)";
        Statement stmt = c1.createStatement();
        stmt.executeUpdate(create);

        DBConnection.setNomDB("testpersonne1");
        c1 = DBConnection.getConnection();
        stmt = c1.createStatement();
        stmt.executeUpdate(create);

        String suppr = "DROP TABLE Test";
        stmt.executeUpdate(suppr);

        DBConnection.setNomDB("testpersonne");
        c1 = DBConnection.getConnection();
        stmt = c1.createStatement();
        stmt.executeUpdate(suppr);

    }
}