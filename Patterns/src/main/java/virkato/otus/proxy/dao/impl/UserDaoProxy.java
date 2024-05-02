package virkato.otus.proxy.dao.impl;

import virkato.otus.proxy.dao.BaseDao;
import virkato.otus.proxy.entity.User;

import java.sql.Connection;
import java.sql.SQLException;


public class UserDaoProxy implements BaseDao {
    private final Connection connection;
    private final BaseDao dao;

    public UserDaoProxy(Connection connection) {
        this.connection = connection;
        this.dao = new UserDao(connection);
    }


    @Override
    public User create(User user) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            user = dao.create(user);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        connection.setAutoCommit(autoCommit);
        return user;
    }


    @Override
    public User read(Long id) throws SQLException {
        User user;
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(true);
        user = dao.read(id);
        connection.setAutoCommit(autoCommit);
        return user;
    }


    @Override
    public void update(User user) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        try {
            dao.update(user);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        connection.setAutoCommit(autoCommit);
    }


    @Override
    public boolean delete(long id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        boolean result = false;
        try {
            result = dao.delete(id);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        connection.setAutoCommit(autoCommit);
        return result;
    }
}
