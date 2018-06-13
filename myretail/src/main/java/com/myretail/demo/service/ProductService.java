package com.myretail.demo.service;

import com.google.gson.JsonObject;
import com.myretail.demo.domain.Product;
import com.myretail.demo.domain.ProductPrice;
import com.myretail.demo.exception.DatabaseException;
import com.myretail.demo.exception.ProductMismatchException;
import com.myretail.demo.exception.ProductNotFoundException;
import com.myretail.demo.httpclient.HttpClientPool;
import com.myretail.demo.repository.ProductPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  ProductService for main logic for saving and getting products
 */
@Service
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String endpoint = "https://redsky.target.com/v2/pdp/tcin/%s?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    @Autowired
    private ProductPriceRepository productPriceRepository;

    /**
     * Retrieves a Product from Http response and db entry
     * @param id productId to read
     * @return Product
     */
    public Product getProductById(long id) {
        ProductPrice productPrice = productPriceRepository.findByProductId(id);
        if (productPrice == null) {
            log.debug("No productprice information for product {}", id);
            throw new ProductNotFoundException("Pricing information not found.");
        }
        JsonObject json = HttpClientPool.INSTANCE.requestURI(String.format(endpoint, id));
        log.debug("Found product information from Http request: {}", getProductName(json));
        return new Product(id, getProductName(json), productPrice);
    }

    /**
     * Updates the DB information for a given product and return the constructed Product
     * @param id productId from path parameter
     * @param product Product object with updated ProductPrice information
     * @return Updated Product
     */
    public Product saveProductById(long id, Product product) {
        if (product.getId() != id) {
            log.debug("Error saving productId mismatched with requested id: {} | {}", id, product.getId());
            throw new ProductMismatchException("Request product id does not match path product id.");
        }
        product.getProductPrice().setProductId(id);
        ProductPrice productPrice = productPriceRepository.save(product.getProductPrice());
        if (productPrice == null) {
            log.debug("Error retrieving pricing information after update");
            throw new DatabaseException("Error updating product pricing information.");
        }
        JsonObject json = HttpClientPool.INSTANCE.requestURI(String.format(endpoint, product.getId()));
        log.debug("Found product information from Http request: {}", getProductName(json));
        product.setProductPrice(productPrice);
        product.setName(getProductName(json));
        return product;
    }

    /**
     * Parse the title from HttpResponse json from redskytarget
     * @param json json returned from HttpClient
     * @return String with title
     */
    private String getProductName(JsonObject json) {
        return json.getAsJsonObject("product").getAsJsonObject("item").getAsJsonObject("product_description").get("title").getAsString();
    }
}
