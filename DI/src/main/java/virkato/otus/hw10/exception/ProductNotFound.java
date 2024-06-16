package virkato.otus.hw10.exception;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound() {
        super("Product not found");
    }
}
