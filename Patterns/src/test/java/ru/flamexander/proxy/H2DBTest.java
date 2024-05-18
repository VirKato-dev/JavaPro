package ru.flamexander.proxy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.flamexander.proxy.dao.impl.UserDaoProxy;
import ru.flamexander.proxy.connection.H2DB;
import ru.flamexander.proxy.dao.BaseDao;
import ru.flamexander.proxy.dao.impl.UserDaoProxy;
import ru.flamexander.proxy.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


class H2DBTest {
    static Connection connection = H2DB.getConnection();

    //        BaseDao dao = new UserDao(connection);
    BaseDao dao = new UserDaoProxy(connection);


    @BeforeAll
    static void setUp() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(true);
            stmt.execute("CREATE TABLE if not exists users (id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), birthday VARCHAR(20))");
        }
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    @Test
    @DisplayName("Проверка работы с базой")
    void getConnection() {
        try {
            assertNotNull(connection);

            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE if not exists persons (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20), birthday VARCHAR(20))");
            stmt.executeUpdate("INSERT INTO persons (name, birthday) VALUES ('John', '2000-01-01')");
            stmt.executeUpdate("INSERT INTO persons (name, birthday) VALUES ('Alex', '2002-02-02')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM persons WHERE id=2");
            while (rs.next()) {
                assertEquals(2, rs.getInt("id"));
                assertEquals("Alex", rs.getString("name"));
                assertEquals("2002-02-02", rs.getString("birthday"));
            }

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
    @DisplayName("Неудачное обновление")
    void updateFail() throws SQLException {
        User user = new User("John", "2000-01-01");
        user = dao.create(user);
        Long id = user.getId();

        assertNotNull(id);

        user.setName("Ben");
        user.setId(99);
        dao.update(user);

        user = dao.read(id);
        assertEquals("John", user.getName());
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