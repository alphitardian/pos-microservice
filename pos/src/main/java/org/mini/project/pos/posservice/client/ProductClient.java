package org.mini.project.pos.posservice.client;

import org.mini.project.pos.posservice.config.WebClientConfiguration;
import org.mini.project.pos.posservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "${microservice.name-service.product}",
    url = "${microservice.url-mapping.product}",
    configuration = WebClientConfiguration.class
)
public interface ProductClient {

  @GetMapping("/products")
  ResponseEntity<List<Product>> findAllProduct();

  @GetMapping("/products/{id}")
  ResponseEntity<Product> findProductById(@PathVariable("id") Long id);

  @PostMapping("/products")
  ResponseEntity<Object> addProduct(@RequestBody Product product);

  @PutMapping("/products/{id}")
  ResponseEntity<Object> updateProductById(
      @PathVariable("id") long id,
      @RequestBody Product product
  );

  @DeleteMapping("/products/{id}")
  ResponseEntity<String> deleteProductById(@PathVariable("id") long id);

  @PutMapping("/products/subtract/{id}/{number}")
  ResponseEntity<Object> subtractProductById(
      @PathVariable("id") long id,
      @PathVariable("number") int number
  );

  @PutMapping("/products/add/{id}/{number}")
  ResponseEntity<Object> addProductById(
      @PathVariable("id") long id,
      @PathVariable("number") int number
  );
}
