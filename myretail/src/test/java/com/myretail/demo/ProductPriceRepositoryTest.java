package com.myretail.demo;

import com.myretail.demo.repository.ProductPriceRepository;
import com.myretail.demo.domain.ProductPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPriceRepositoryTest {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Before
    public void setup() {
        productPriceRepository.deleteAll();
    }

    @Test
    public void testRepoSaveProductPrice() throws Exception {
        ProductPrice productPrice = new ProductPrice(1L, new BigDecimal(1.00), "USD");
        productPriceRepository.save(productPrice);
        ProductPrice productPriceRead = productPriceRepository.findByProductId(1L);
        assertEquals(productPrice.getCurrency(), productPriceRead.getCurrency());
        assertEquals(productPrice.getValue(), productPriceRead.getValue());
    }

    @Test
    public void testRepoUpdateProductPrice() throws Exception {
        long productId = 2;
        ProductPrice productPrice = new ProductPrice(productId, new BigDecimal(1.00), "USD");
        productPriceRepository.save(productPrice);

        ProductPrice productPrice1 = productPriceRepository.findByProductId(productId);
        productPrice1.setCurrency("CAN");
        productPrice1.setValue(new BigDecimal(2.00));

        productPriceRepository.save(productPrice1);
        ProductPrice productPrice2 = productPriceRepository.findByProductId(productId);

        assertEquals(productPrice.getProductId(), productPrice2.getProductId());
        assertNotEquals(productPrice.getCurrency(), productPrice2.getCurrency());
        assertNotEquals(productPrice.getValue(), productPrice2.getValue());
    }
}
