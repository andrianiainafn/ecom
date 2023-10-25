package org.sid.orderservice;

import org.sid.orderservice.entities.Order;
import org.sid.orderservice.entities.ProductItem;
import org.sid.orderservice.enums.OrderStatus;
import org.sid.orderservice.model.Customer;
import org.sid.orderservice.model.Product;
import org.sid.orderservice.repository.OrderRepository;
import org.sid.orderservice.repository.ProductItemRepository;
import org.sid.orderservice.services.CustomerRestService;
import org.sid.orderservice.services.InventoryRestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            OrderRepository orderRepository,
            ProductItemRepository productItemRepository,
            CustomerRestService customerRestService,
            InventoryRestService inventoryRestService
    ){
        return args -> {
            List<Customer> customers = customerRestService.allCustomers().getContent().stream().toList();
            List<Product> products = inventoryRestService.allProducts().getContent().stream().toList();
            Long customerId = 1L;
            Customer customer = customerRestService.customerById(customerId);
            Random random = new Random();
            for (int i = 0;i< 20 ; i++){
                Order order= Order.builder()
                        .customerId(customers.get(random.nextInt(customers.size())).getId())
                        .status(Math.random()>0.5? OrderStatus.PENDING:OrderStatus.CREATED)
                        .createdAt(new Date())
                        .build();
                Order savedOrder  = orderRepository.save(order);
                for (int j = 0 ; j < products.size() ; j++){

                        ProductItem  productItem = ProductItem.builder()
                                .order(savedOrder)
                                .productId(1L)
                                .price(products.get(j).getPrice())
                                .quantity(random.nextInt(10))
                                .discount(Math.random())
                                .build();
                        productItemRepository.save(productItem);

                }
            }
        };
    }
}
