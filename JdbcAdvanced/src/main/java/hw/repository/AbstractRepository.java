package hw.repository;

import db.interaction.ex05_repositories.DataSource;
import hw.exception.RepositoryException;

import java.util.List;

public abstract class AbstractRepository<T> {
    private DataSource dataSource;

    public AbstractRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // CRUD
    protected abstract void create(T entity) throws RepositoryException;

    protected abstract T findById(long id) throws RepositoryException;

    protected abstract List<T> findAll() throws RepositoryException;

    protected abstract int update(T entity) throws RepositoryException;

    protected abstract void deleteById(long id) throws RepositoryException;

    protected abstract void deleteAll() throws RepositoryException;
}
