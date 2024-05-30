package hw.repository.impl;

import db.interaction.ex05_repositories.DataSource;
import hw.entity.Account;
import hw.exception.RepositoryException;
import hw.repository.AbstractRepository;

import java.util.List;

public class AccountRepository extends AbstractRepository<Account> {

    public AccountRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void create(Account entity) throws RepositoryException {

    }

    @Override
    protected Account findById(long id) throws RepositoryException {
        return null;
    }

    @Override
    protected List<Account> findAll() throws RepositoryException {
        return List.of();
    }

    @Override
    protected int update(Account entity) throws RepositoryException {
        return 0;
    }

    @Override
    protected void deleteById(long id) throws RepositoryException {

    }

    @Override
    protected void deleteAll() throws RepositoryException {

    }
}
