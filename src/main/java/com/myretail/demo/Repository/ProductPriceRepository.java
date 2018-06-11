package com.myretail.demo.Repository;

import com.myretail.demo.domain.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends MongoRepository<ProductPrice, String>{//,ProductPriceRepositoryCustom{

    ProductPrice findByProductId(long productId);

}
