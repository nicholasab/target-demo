package com.myretail.demo;

import com.myretail.demo.Repository.ProductPriceRepository;
import com.myretail.demo.domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ProductPriceRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        /*repository.save(new ProductPrice(13860428l, 10.13f, "USD"));
        repository.save(new ProductPrice(13860429l, 10.13f, "USD"));
        repository.save(new ProductPrice(13860429l, 10.14f, "CAN"));*/

        ProductPrice price = repository.findByProductId(13860428);
        ProductPrice price1 = repository.findByProductId(13860429);
    }
}
