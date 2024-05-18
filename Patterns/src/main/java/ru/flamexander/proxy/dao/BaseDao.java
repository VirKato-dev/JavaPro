package ru.flamexander.proxy.dao;

import ru.flamexander.proxy.entity.User;

import java.sql.SQLException;


public interface BaseDao {
    User create(User user) throws SQLException;

    User read(Long id) throws SQLException;

    void update(User user) throws SQLException;

    boolean delete(long id) throws SQLException;
}
