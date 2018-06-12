package com.myretail.demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonPropertyOrder({"id","name","current_price"})
public class Product {

    @NotNull(message = "id must not be null")
    private Long id;

    private String name;

    @NotNull(message = "current_price json must not be null")
    @JsonProperty("current_price")
    @Valid
    private ProductPrice productPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    @JsonCreator
    public Product(Long id, String name, @JsonProperty("current_price") ProductPrice productPrice) {
        this.id = id;
        this.name = name;
        this.productPrice = productPrice;
    }

}
