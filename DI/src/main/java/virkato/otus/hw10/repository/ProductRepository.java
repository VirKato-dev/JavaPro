package virkato.otus.hw10.repository;

import org.springframework.stereotype.Component;
import virkato.otus.hw10.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepository implements Repository<Product> {

    private final List<Product> products = new ArrayList<>();


    @Override
    public Product add(Product entity) {
        products.add(entity);
        entity.setId(products.size());
        return entity;
    }


    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products);
    }


    @Override
    public Optional<Product> get(int id) {
        return products.stream().filter(e -> e.getId() == id).findFirst();
    }


    @Override
    public void delete(int id) {
        products.removeIf(e -> e.getId() == id);
    }
}
