package com.myretail.demo;

import com.myretail.demo.repository.ProductPriceRepository;
import com.myretail.demo.domain.ProductPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

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

        repository.save(new ProductPrice(16696652l, new BigDecimal(5.00), "USD"));
        repository.save(new ProductPrice(13860429l, new BigDecimal(8.91), "USD"));
        repository.save(new ProductPrice(13860428l, new BigDecimal(8.91), "USD"));


    }
}
