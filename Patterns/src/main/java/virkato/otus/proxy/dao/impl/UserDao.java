package virkato.otus.proxy.dao.impl;

import virkato.otus.proxy.dao.BaseDao;
import virkato.otus.proxy.entity.User;

import java.sql.*;


public class UserDao implements BaseDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(User user) throws SQLException {
        String sql = "insert into users (name, birthday) values (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getBirthday());

            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    return user;
                }
            }
        }
        throw new SQLException("Creating user failed, no ID obtained.");
    }

    @Override
    public User read(Long id) throws SQLException {
        String sql = String.format("select * from users where id=%d", id);

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("birthday"));
                user.setId(id);
                return user;
            }
        }
        throw new SQLException("User with id=" + id + " not found.");
    }


    @Override
    public void update(User user) throws SQLException {
        String sql = String.format("update users set name='%s', birthday='%s' where id=%d",
                user.getName(), user.getBirthday(), user.getId());

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }


    @Override
    public boolean delete(long id) throws SQLException {
        String sql = "delete from users where id=" + id;
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql) > 0;
        }
    }
}
