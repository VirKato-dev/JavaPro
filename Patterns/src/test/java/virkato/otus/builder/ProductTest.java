package virkato.otus.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class ProductTest {

    @Test
    void test() {
        Product p = new Product.Builder()
                .title("Хлеб")
                .description("Всему голова")
                .cost(25d)
                .build();
        System.out.println(p);

        assertNotNull(p.getCost());
        assertNull(p.getId());
    }

}