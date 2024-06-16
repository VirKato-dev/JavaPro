package virkato.otus.hw10.service;

import org.springframework.stereotype.Component;
import virkato.otus.hw10.exception.ProductNotFound;
import virkato.otus.hw10.model.Product;
import virkato.otus.hw10.repository.ProductRepository;

import java.util.List;


@Component
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product create(Product product) {
        return productRepository.add(product);
    }


    public List<Product> getAll() {
        return productRepository.getAll();
    }


    public Product get(int id) {
        return productRepository.get(id).orElseThrow(ProductNotFound::new);
    }


    public void delete(int id) {
        productRepository.delete(id);
    }
}
