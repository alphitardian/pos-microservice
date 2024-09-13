package org.mini.project.pos.posservice.client;

import org.mini.project.pos.posservice.config.WebClientConfiguration;
import org.mini.project.pos.posservice.model.customer.Customer;
import org.mini.project.pos.posservice.model.customer.CustomerBalance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "${microservice.name-service.customer}",
    url = "${microservice.url-mapping.customer}",
    configuration = WebClientConfiguration.class
)
public interface CustomerClient {

    @GetMapping("/customers/all")
    List<Customer> getAllCustomers();

    @GetMapping("/customers/{id}")
    Customer getCustomerById(@PathVariable("id") long id);

    @PostMapping("/customers/create")
    Customer createCustomer(@RequestBody Customer customer);

    @PutMapping("/customers/update/info/{id}")
    ResponseEntity<Object> updateCustomer(
        @PathVariable("id") long id,
        @RequestBody Customer customer
    );

    @PutMapping("/customers/update/balance/{id}")
    ResponseEntity<Object> updateCustomerBalance(
        @PathVariable("id") long id,
        @RequestBody CustomerBalance customer
    );

    @DeleteMapping("/customers/delete/{id}")
    Object deleteCustomer(@PathVariable("id") long id);
}
