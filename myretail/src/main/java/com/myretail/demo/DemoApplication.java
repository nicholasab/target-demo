package com.myretail.demo;

import com.myretail.demo.repository.ProductPriceRepository;
import com.myretail.demo.domain.ProductPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductPriceRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Delete and populate initial database entries
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        log.debug("****************************************************");
        log.debug("**    Populating Initial Data                     **");
        log.debug("****************************************************");
        repository.deleteAll();
        repository.save(new ProductPrice(16696652l, new BigDecimal(22), "USD"));
        repository.save(new ProductPrice(13860429l, new BigDecimal(11), "USD"));
        repository.save(new ProductPrice(13860428l, new BigDecimal(12), "USD"));


    }
}
