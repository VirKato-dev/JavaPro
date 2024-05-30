package hw.repository.impl;

import db.interaction.ex05_repositories.DataSource;
import hw.annotation.SqlQueryMaker;
import hw.entity.Product;
import hw.exception.RepositoryException;
import hw.repository.AbstractRepository;

import java.util.List;

public class ProductRepository extends AbstractRepository<Product> {

    public ProductRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void create(Product entity) throws RepositoryException {
        String sql = SqlQueryMaker.queryInsert(entity);
    }

    @Override
    protected Product findById(long id) throws RepositoryException {
        return null;
    }

    @Override
    protected List<Product> findAll() throws RepositoryException {
        return List.of();
    }

    @Override
    protected int update(Product entity) throws RepositoryException {
        return 0;
    }

    @Override
    protected void deleteById(long id) throws RepositoryException {

    }

    @Override
    protected void deleteAll() throws RepositoryException {

    }
}
