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
        connection.setAutoCommit(false);
        try {
            user = dao.create(user);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        return user;
    }


    @Override
    public User read(Long id) throws SQLException {
        connection.setAutoCommit(true);
        return dao.read(id);
    }


    @Override
    public void update(User user) throws SQLException {
        connection.setAutoCommit(false);
        try {
            dao.update(user);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }


    @Override
    public boolean delete(long id) throws SQLException {
        connection.setAutoCommit(false);
        boolean result = false;
        try {
            result = dao.delete(id);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        return result;
    }
}
