package virkato.otus.proxy.dao;

import virkato.otus.proxy.entity.User;

import java.sql.SQLException;


public interface BaseDao {
    User create(User user) throws SQLException;

    User read(Long id) throws SQLException;

    boolean update(User user) throws SQLException;

    boolean delete(long id) throws SQLException;
}
