package com.myretail.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.demo.Repository.ProductPriceRepository;
import com.myretail.demo.product.ProductController;
import com.myretail.demo.service.ProductService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @MockBean
    ProductPriceRepository productPriceRepository;

    @Autowired
    ObjectMapper objectMapper;

}
