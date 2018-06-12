package com.myretail.demo.Repository;

import com.myretail.demo.domain.ProductPrice;

public interface ProductPriceRepositoryCustom {

    ProductPrice saveByProductId(ProductPrice productPrice);

}
