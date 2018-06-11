package com.myretail.demo.product;

import com.myretail.demo.domain.Product;
import com.myretail.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products/{id}")
    public Product get(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PutMapping(value = "/products/{id}")
    public Product put(@PathVariable("id") Long id, @Validated @RequestBody Product product) {
        return productService.saveProductById(id, product);
    }


}
