package hw.entity;

import hw.annotation.RepositoryField;
import hw.annotation.RepositoryIdField;
import hw.annotation.RepositoryTable;

@RepositoryTable(name = "product")
public class Product {
    @RepositoryIdField
    @RepositoryField
    private Long id;

    @RepositoryField
    private String title;

    @RepositoryField
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
