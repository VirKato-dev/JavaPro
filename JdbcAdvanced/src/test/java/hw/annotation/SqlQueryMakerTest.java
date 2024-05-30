package hw.annotation;

import hw.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SqlQueryMakerTest {

    @Test
    void makeSqlQuery() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test");
        product.setPrice(10d);

        String expected = "INSERT INTO `product` (`title`,`price`) VALUES ('Test',10.0);";
        assertEquals(expected, SqlQueryMaker.queryInsert(product));
    }
}