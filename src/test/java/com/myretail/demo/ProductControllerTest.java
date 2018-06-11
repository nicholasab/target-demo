package com.myretail.demo;

import com.myretail.demo.Repository.ProductPriceRepository;
import com.myretail.demo.product.ProductController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductPriceRepository productPriceRepository;

    @Test
    public void shouldAcceptValidDataEn() throws Exception {
        this.mvc.perform(put("/products/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":\"5\"," +
                        "\"name\":\"testname\"," +
                        "\"current_price\":{" +
                        "\"value\":11.99," +
                        "\"currency\":\"USD\"" +
                        "}}"))
                .andExpect(status().isOk());
    }

}
