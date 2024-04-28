package virkato.otus.proxy.dao.impl;

import virkato.otus.proxy.connection.H2DB;
import virkato.otus.proxy.dao.BaseDao;
import virkato.otus.proxy.entity.User;

import java.sql.Connection;
import java.sql.SQLException;


public class UserDaoProxy implements BaseDao {
    private final BaseDao dao = new UserDao();
    private Connection connection;


    @Override
    public User create(User user) throws SQLException {
        connection = H2DB.getConnection();
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
        connection = H2DB.getConnection();
        connection.setAutoCommit(true);
        return dao.read(id);
    }


    @Override
    public void update(User user) throws SQLException {
        connection = H2DB.getConnection();
        connection.setAutoCommit(false);
        boolean result = false;
        try {
            dao.update(user);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }


    @Override
    public boolean delete(long id) throws SQLException {
        connection = H2DB.getConnection();
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
