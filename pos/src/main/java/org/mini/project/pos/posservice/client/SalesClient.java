package org.mini.project.pos.posservice.client;

import org.mini.project.pos.posservice.config.WebClientConfiguration;
import org.mini.project.pos.posservice.model.Sales;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "${microservice.name-service.sales}",
    url = "${microservice.url-mapping.sales}",
    configuration = WebClientConfiguration.class
)
public interface SalesClient {

  @GetMapping("/sales")
  ResponseEntity<Object> findAllSales();

  @GetMapping("/sales/{id}")
  ResponseEntity<Object> findSalesById(@PathVariable("id") long id);

  @PostMapping("/sales")
  ResponseEntity<Object> addSales(@RequestBody Sales sales);
}
