package org.mini.project.pos.posservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mini.project.pos.posservice.client.ProductClient;
import org.mini.project.pos.posservice.client.SalesClient;
import org.mini.project.pos.posservice.helper.ObjectMapperUtil;
import org.mini.project.pos.posservice.model.Product;
import org.mini.project.pos.posservice.model.Sales;
import org.mini.project.pos.posservice.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PosService {

  @Autowired
  private ProductClient productClient;

  @Autowired
  private SalesClient salesClient;

  public Mono<ResponseEntity<List<Product>>> productStockInquiry() {
    return Mono.fromCallable(() -> this.productClient.findAllProduct())
        .map(objectResponseEntity -> {
          List<Product> products = objectResponseEntity.getBody();
          log.info("Product list: {}", ObjectMapperUtil.writeValueAsString(products));

          return ResponseEntity
              .status(HttpStatus.OK)
              .body(products);
        });
  }

  public Mono<ResponseEntity<Transaction>> createTransaction(Transaction transaction) {
    return Mono.fromCallable(() -> productClient.findProductById(transaction.getProductId()))
        .flatMap(productResponseEntity -> {
          Product product = productResponseEntity.getBody();
          transaction.setProduct(product);

          assert product != null;
          Sales sales = Sales.builder()
              .posId(UUID.randomUUID().toString())
              .productId(String.valueOf(product.getId()))
              .quantity(transaction.getQuantity())
              .amount(product.getPrice() * transaction.getQuantity())
              .build();

          return Mono.just(sales);
        })
        .flatMap(sales -> Mono.fromCallable(() ->  salesClient.addSales(sales)))
        .flatMap(objectResponseEntity -> {
          if (objectResponseEntity.getStatusCode().is2xxSuccessful()) {
            Sales sales = ObjectMapperUtil.convertMapToValue((Map<String, Object>) objectResponseEntity.getBody(), Sales.class);
            transaction.setSales(sales);

            return Mono.just(sales);
          }

          return Mono.empty();
        })
        // TODO: Add customerClient
        .thenReturn(
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transaction)
        );
  }
}
