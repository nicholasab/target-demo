package com.myretail.demo;

import com.myretail.demo.repository.ProductPriceRepository;
import com.myretail.demo.domain.Product;
import com.myretail.demo.domain.ProductPrice;
import com.myretail.demo.exception.ProductMismatchException;
import com.myretail.demo.exception.ProductNotFoundException;
import com.myretail.demo.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductService productService;

    @Before
    public void setup() {
        productPriceRepository.deleteAll();
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

    @Test(expected = ResponseStatusException.class)
    public void testServiceSaveRetrieveProductName() throws Exception {
        Product product = new Product(1L, "name", new ProductPrice(2L, new BigDecimal(1.00), "USD"));
        productService.saveProductById(1L, product);
    }

    @Test
    public void testServiceSaveById() throws Exception {
        Product product = new Product(16696652L, "name", new ProductPrice(16696652L, new BigDecimal(1.00), "USD"));
        productService.saveProductById(16696652L, product);
        Product product1 = productService.getProductById(16696652L);
        assertTrue(product.equals(product1));
    }
}
