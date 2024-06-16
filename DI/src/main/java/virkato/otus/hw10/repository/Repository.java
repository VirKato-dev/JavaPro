package virkato.otus.hw10.repository;

import java.util.List;
import java.util.Optional;


public interface Repository<T> {
    T add(T entity);

    List<T> getAll();

    Optional<T> get(int id);

    void delete(int id);
}
