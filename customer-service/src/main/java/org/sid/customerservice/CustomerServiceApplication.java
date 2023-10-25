package org.sid.customerservice;

import org.sid.customerservice.entites.Customer;
import org.sid.customerservice.repo.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class    CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository){
        return args -> {
            customerRepository.saveAll(List.of(
                    Customer.builder().name("Yor").email("Yor@gmail.com").build(),
                    Customer.builder().name("Nezuko").email("Nezuko@gmail.com").build(),
                    Customer.builder().name("Tsunade").email("Tsunade@gmail.com").build()
            ));
            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
