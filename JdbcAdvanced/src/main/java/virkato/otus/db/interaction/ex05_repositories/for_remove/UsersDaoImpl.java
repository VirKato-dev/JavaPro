package virkato.otus.db.interaction.ex05_repositories.for_remove;

import virkato.otus.db.interaction.ex05_repositories.DataSource;
import virkato.otus.db.interaction.ex05_repositories.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersDaoImpl implements UsersDao {
    private DataSource dataSource;

    public UsersDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> out = new ArrayList<>();
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from users;")) {
            while (rs.next()) {
                User user = new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"), rs.getString("nickname"));
                out.add(user);
            }
        }
        return Collections.unmodifiableList(out);
    }
}
