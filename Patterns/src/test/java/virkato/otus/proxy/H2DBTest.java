package virkato.otus.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import virkato.otus.proxy.connection.H2DB;
import virkato.otus.proxy.dao.BaseDao;
import virkato.otus.proxy.dao.impl.UserDao;
import virkato.otus.proxy.dao.impl.UserDaoProxy;
import virkato.otus.proxy.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


class H2DBTest {

//    BaseDao dao = new UserDao();
    BaseDao dao = new UserDaoProxy();


    @Test
    @DisplayName("Проверка работы с базой")
    void getConnection() {
        Connection connection = H2DB.getConnection();
        try {
            assertNotNull(connection);

            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE if not exists persons (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), birthday VARCHAR(20))");

            stmt.executeUpdate("INSERT INTO persons (name, birthday) VALUES ('John', '2000-01-01')");
            stmt.executeUpdate("INSERT INTO persons (name, birthday) VALUES ('Alex', '2002-02-02')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM persons WHERE id = 2");
            while (rs.next()) {
                assertEquals(2, rs.getInt("id"));
                assertEquals("Alex", rs.getString("name"));
                assertEquals("2002-02-02", rs.getString("birthday"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    @DisplayName("Добавить запись")
    void create() throws SQLException {
        User user = new User("John", "2000-01-01");
        user = dao.create(user);
        assertEquals(1, user.getId());
    }


    @Test
    @DisplayName("Обновить запись и прочитать")
    void updateAndRead() throws SQLException {
        User user = new User("John", "2000-01-01");
        user = dao.create(user);
        Long id = user.getId();

        assertNotNull(id);

        user.setName("Ben");
        dao.update(user);
        assertEquals(id, user.getId());

        user = dao.read(id);
        assertEquals("Ben", user.getName());
    }


    @Test
    @DisplayName("Удалить запись")
    void delete() throws SQLException {
        User user = new User("John", "2000-01-01");
        user = dao.create(user);

        assertNotNull(user.getId());

        assertTrue(dao.delete(user.getId()));
        assertFalse(dao.delete(9));
    }
}