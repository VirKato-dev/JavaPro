package virkato.otus.proxy.dao.impl;

import virkato.otus.proxy.connection.H2DB;
import virkato.otus.proxy.dao.BaseDao;
import virkato.otus.proxy.entity.User;

import java.sql.*;


public class UserDao implements BaseDao {

    private Connection connection;

    @Override
    public User create(User user) throws SQLException {
        connection = H2DB.getConnection();
        String sql = "insert into users (name, birthday) values (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getName());
        statement.setString(2, user.getBirthday());

        int rows = statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();

        if (rows > 0) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
                statement.close();
                return user;
            }
        }
        statement.close();
        throw new SQLException("Creating user failed, no ID obtained.");
    }

    @Override
    public User read(Long id) throws SQLException {
        connection = H2DB.getConnection();
        String sql = String.format("select * from users where id=%d", id);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            User user = new User(rs.getString("name"), rs.getString("birthday"));
            user.setId(id);
            statement.close();
            return user;
        }
        statement.close();
        throw new SQLException("User with id=" + id + " not found.");
    }


    @Override
    public void update(User user) throws SQLException {
        connection = H2DB.getConnection();
        String sql = String.format("update users set name='%s', birthday='%s' where id=%d",
                user.getName(), user.getBirthday(), user.getId());

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }


    @Override
    public boolean delete(long id) throws SQLException {
        connection = H2DB.getConnection();
        String sql = "delete from users where id=" + id;
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(sql);
        statement.close();
        return result > 0;
    }
}
