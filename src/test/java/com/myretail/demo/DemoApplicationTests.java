package com.myretail.demo;

import com.myretail.demo.Repository.ProductPriceRepository;
import com.myretail.demo.domain.Product;
import com.myretail.demo.domain.ProductPrice;
import com.myretail.demo.exception.ProductMismatchException;
import com.myretail.demo.exception.ProductNotFoundException;
import com.myretail.demo.service.ProductService;
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
public class DemoApplicationTests {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void contextLoads() {
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

    @Test(expected = ProductMismatchException.class)
    public void testServicePutMismatch() throws Exception {
        Product product = new Product(2L, "name", new ProductPrice(2L, new BigDecimal(1.00), "USD"));
        productService.saveProductById(1L, product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testServiceGetNotFound() throws Exception {
        productService.getProductById(3L);
    }


}
