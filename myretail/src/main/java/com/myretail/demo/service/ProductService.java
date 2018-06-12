package com.myretail.demo.service;

import com.google.gson.JsonObject;
import com.myretail.demo.HttpConnectionPool.HttpClientPool;
import com.myretail.demo.Repository.ProductPriceRepository;
import com.myretail.demo.domain.Product;
import com.myretail.demo.domain.ProductPrice;
import com.myretail.demo.exception.DatabaseException;
import com.myretail.demo.exception.ProductMismatchException;
import com.myretail.demo.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final String endpoint = "https://redsky.target.com/v2/pdp/tcin/%s?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    @Autowired
    private ProductPriceRepository productPriceRepository;

    public Product getProductById(long id) {
        ProductPrice productPrice = productPriceRepository.findByProductId(id);
        if (productPrice == null) {
            throw new ProductNotFoundException("Pricing information not found.");
        }

        JsonObject json = HttpClientPool.INSTANCE.requestURI(String.format(endpoint, id));
        return new Product(id, getProductName(json), productPrice);
    }

    public Product saveProductById(long id, Product product) {
        if (product.getId() != id) {
            throw new ProductMismatchException("Request product id does not match path product id.");
        }
        product.getProductPrice().setProductId(id);
        ProductPrice productPrice = productPriceRepository.save(product.getProductPrice());
        if (productPrice == null) {
            throw new DatabaseException("Error updating product pricing information.");
        }
        JsonObject json = HttpClientPool.INSTANCE.requestURI(String.format(endpoint, product.getId()));

        product.setProductPrice(productPrice);
        product.setName(getProductName(json));
        return product;
    }

    private String getProductName(JsonObject json) {
        return json.getAsJsonObject("product").getAsJsonObject("item").getAsJsonObject("product_description").get("title").getAsString();
    }
}
