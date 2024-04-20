package virkato.otus.proxy.dao.impl;

import virkato.otus.proxy.connection.H2DB;
import virkato.otus.proxy.dao.BaseDao;
import virkato.otus.proxy.entity.User;

import java.sql.Connection;
import java.sql.SQLException;


public class UserDaoProxy implements BaseDao {
    private final BaseDao dao = new UserDao();
    private final Connection connection = H2DB.getConnection();


    @Override
    public User create(User user) throws SQLException {
        connection.setAutoCommit(false);
        try {
            user = dao.create(user);
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return user;
    }


    @Override
    public User read(Long id) throws SQLException {
        connection.setAutoCommit(false);
        return dao.read(id);
    }


    @Override
    public boolean update(User user) throws SQLException {
        connection.setAutoCommit(false);
        boolean result = false;
        try {
            result = dao.update(user);
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return result;
    }


    @Override
    public boolean delete(long id) throws SQLException {
        connection.setAutoCommit(false);
        boolean result = false;
        try {
            result = dao.delete(id);
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return result;
    }
}
