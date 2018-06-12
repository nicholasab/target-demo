package com.myretail.demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document(collection = "productprice")
public class ProductPrice {

    @JsonIgnore
    @Id
    private Long productId;
    @NotNull(message = "current_price.value must not be null")
    private BigDecimal value;

    @NotNull(message = "current_price.currency_code must not be null")
    @JsonProperty("currency_code")
    private String currency;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonCreator
    public ProductPrice(Long productId, BigDecimal value, @JsonProperty("currency_code") String currency) {
        this.productId = productId;
        this.value = value;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof ProductPrice)){
            return false;
        }
        ProductPrice ob = (ProductPrice) obj;
        return (productId.equals(ob.productId) && value.equals(ob.value) && currency.equals(ob.currency));
    }
}
