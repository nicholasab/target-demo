package com.myretail.demo;

import com.myretail.demo.Repository.ProductPriceRepository;
import com.myretail.demo.domain.ProductPrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPriceRepositoryTest {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Test
    public void saveProductPrice() throws Exception{
        productPriceRepository.save(new ProductPrice(1L,"1.00","USD"));
        ProductPrice productPrice = productPriceRepository.findByProductId(1L);
        assertEquals("USD",productPrice.getCurrency());
        assertEquals("1.00",productPrice.getValue());
    }

    @Test
    public void updateProductPrice() throws Exception{
        long productId = 2;
        productPriceRepository.save(new ProductPrice(productId,"1.00","USD"));
        ProductPrice productPrice = productPriceRepository.findByProductId(productId);
        productPrice.setCurrency("CAN");
        productPrice.setValue("2.00");
        productPriceRepository.saveByProductId(productPrice);
        ProductPrice productPrice2 = productPriceRepository.findByProductId(productId);

        assertEquals(productPrice.getCurrency(),productPrice2.getCurrency());
        assertEquals(productPrice.getValue(),productPrice2.getValue());
    }
}
