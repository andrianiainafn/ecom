package org.sid.inventoryservice;

import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner start(ProductRepository productRepository){
        return args -> {
          productRepository.saveAll(List.of(
                  Product.builder().id(1L).name("MacBook").price(4000).quantity(3).build(),
                  Product.builder().id(2L).name("Magic Mouse").price(150).quantity(3).build(),
                  Product.builder().id(3L).name("Magic Keyboard").price(300).quantity(3).build()
          ));
           productRepository.findAll().forEach(System.out::println);
        };
    }
}
