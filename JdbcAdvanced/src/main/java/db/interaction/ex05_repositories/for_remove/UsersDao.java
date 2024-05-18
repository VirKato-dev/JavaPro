package db.interaction.ex05_repositories.for_remove;

import db.interaction.ex05_repositories.User;

import java.sql.SQLException;
import java.util.List;

public interface UsersDao {
    List<User> findAll() throws SQLException;
}
