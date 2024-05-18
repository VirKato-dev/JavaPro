package ru.flamexander.builder;

import java.util.StringJoiner;

public class Product {
    private Long id;
    private String title;
    private String description;
    private Double cost;
    private Double weight;
    private Integer width;
    private Integer length;
    private Integer height;

    public static class Builder {
        private final Product product;

        public Builder() {
            product = new Product();
        }

        public Product build() {
            return product;
        }

        public Builder id(Long id) {
            product.id = id;
            return this;
        }

        public Builder title(String title) {
            product.title = title;
            return this;
        }

        public Builder description(String description) {
            product.description = description;
            return this;
        }

        public Builder cost(Double cost) {
            product.cost = cost;
            return this;
        }

        public Builder weight(Double weight) {
            product.weight = weight;
            return this;
        }

        public Builder width(Integer width) {
            product.width = width;
            return this;
        }

        public Builder length(Integer length) {
            product.length = length;
            return this;
        }

        public Builder height(Integer height) {
            product.height = height;
            return this;
        }
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "Product{", "}");
        if (id != null) sj.add("id=" + id);
        if (title != null) sj.add("title='" + title + "'");
        if (description != null) sj.add("description='" + description + "'");
        if (cost != null) sj.add("cost=" + cost);
        if (weight != null) sj.add("weight=" + weight);
        if (width != null) sj.add("width=" + width);
        if (length != null) sj.add("length=" + length);
        if (height != null) sj.add("height=" + height);
        return sj.toString();
    }
}
