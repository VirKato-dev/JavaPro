package virkato.otus.hw10.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import virkato.otus.hw10.exception.ProductNotFound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Cart {

    List<Product> products = new ArrayList<>();


    public void add(Product product) {
        try {
            products.add(product);
        } catch (ProductNotFound e) {
            System.out.println(e.getMessage());
        }
    }


    public void remove(Product product) {
        Iterator<Product> it = products.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(product.getId())) {
                it.remove();
                break;
            }
        }
    }


    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("-".repeat(10) + "\nYour products:\n");
        if (products.isEmpty()) {
            sb.append("Is empty yet.");
        } else {
            for (Product product : products) {
                sb.append(product);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
