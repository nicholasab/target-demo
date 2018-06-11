package com.myretail.demo.Repository;

import com.mongodb.client.result.UpdateResult;
import com.myretail.demo.domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

public class ProductPriceRepositoryImpl implements ProductPriceRepositoryCustom {

    @Autowired
    private MongoTemplate template;

    @Override
    public ProductPrice saveByProductId(ProductPrice productPrice) {
        // query to find productid
        Query productIdQuery = new Query(Criteria.where("productId").is(productPrice.getProductId()));
        Update update = new Update();
        update.set("value", productPrice.getValue());
        update.set("currency", productPrice.getCurrency());
        UpdateResult result = template.upsert(productIdQuery, update, ProductPrice.class);

        if (result.getModifiedCount() != 1) {

        }
        return template.findOne(productIdQuery, ProductPrice.class);
    }
}
