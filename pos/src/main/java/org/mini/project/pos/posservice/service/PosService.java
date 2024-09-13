package org.mini.project.pos.posservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mini.project.pos.posservice.client.CustomerClient;
import org.mini.project.pos.posservice.client.ProductClient;
import org.mini.project.pos.posservice.client.SalesClient;
import org.mini.project.pos.posservice.helper.ObjectMapperUtil;
import org.mini.project.pos.posservice.model.customer.Customer;
import org.mini.project.pos.posservice.model.Product;
import org.mini.project.pos.posservice.model.Sales;
import org.mini.project.pos.posservice.model.customer.CustomerBalance;
import org.mini.project.pos.posservice.model.transaction.Transaction;
import org.mini.project.pos.posservice.model.transaction.TransactionCustomer;
import org.mini.project.pos.posservice.model.transaction.TransactionProduct;
import org.mini.project.pos.posservice.model.transaction.TransactionReport;
import org.mini.project.pos.posservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PosService {

  @Autowired
  private ProductClient productClient;

  @Autowired
  private SalesClient salesClient;

  @Autowired
  private CustomerClient customerClient;

  @Autowired
  private TransactionRepository transactionRepository;

  public Mono<ResponseEntity<List<Product>>> productStockInquiry() {
    log.info("Start inquiry product stock");

    return Mono.fromCallable(() -> this.productClient.findAllProduct())
        .map(objectResponseEntity -> {
          List<Product> products = objectResponseEntity.getBody();
          log.info("Current product list: {}", ObjectMapperUtil.writeValueAsString(products));

          return ResponseEntity
              .status(HttpStatus.OK)
              .body(products);
        });
  }

  public Mono<ResponseEntity<Transaction>> createTransaction(Transaction transaction) {
    log.info("Start creating transaction with payload: {}", ObjectMapperUtil.writeValueAsString(transaction));

    return Mono.fromCallable(() -> productClient.findProductById(transaction.getProductId()))
        .flatMap(productResponseEntity -> {
          Product product = productResponseEntity.getBody();
          log.info("Product with id {} found: {}", transaction.getProductId(), ObjectMapperUtil.writeValueAsString(product));

          assert product != null;
          TransactionProduct transactionProduct = TransactionProduct.builder()
              .id(product.getId())
              .productName(product.getProductName())
              .description(product.getDescription())
              .price(product.getPrice())
              .build();
          transaction.setProduct(transactionProduct);

          Sales sales = Sales.builder()
              .posId(UUID.randomUUID().toString())
              .productId(String.valueOf(product.getId()))
              .quantity(transaction.getQuantity())
              .amount(product.getPrice() * transaction.getQuantity())
              .build();

          return Mono.just(sales);
        })
        .flatMap(sales -> Mono.fromCallable(() -> salesClient.addSales(sales)))
        .flatMap(objectResponseEntity -> {
          if (objectResponseEntity.getStatusCode().is2xxSuccessful()) {
            Sales sales = ObjectMapperUtil.convertMapToValue((Map<String, Object>) objectResponseEntity.getBody(), Sales.class);
            transaction.setSales(sales);
            log.info("Success creating sales data: {}", ObjectMapperUtil.writeValueAsString(sales));

            return Mono.just(sales);
          }

          return Mono.empty();
        })
        .flatMap(sales -> Mono.fromCallable(() -> productClient.subtractProductById(
                Long.parseLong(sales.getProductId()),
                sales.getQuantity())
        ))
        .flatMap(objectResponseEntity -> {
            if (objectResponseEntity.getStatusCode().is2xxSuccessful()) {
                Product product = ObjectMapperUtil.convertMapToValue((Map<String, Object>) objectResponseEntity.getBody(), Product.class);
                log.info("Success update stock after purchase: {}", ObjectMapperUtil.writeValueAsString(product));

                return Mono.just(product);
            }

            return Mono.just(transaction);
        })
        .flatMap(unused -> Mono.fromCallable(() -> customerClient.getCustomerById(transaction.getCustomerId())))
        .flatMap(customer -> {
            List<Long> currentOrderIds = customer.getOrderId();
            currentOrderIds.add(transaction.getSales().getId());
            customer.setOrderId(currentOrderIds);
            log.info("Getting customer with id {}: {}", transaction.getCustomerId(), ObjectMapperUtil.writeValueAsString(customer));

            // Temporary save customer
            TransactionCustomer transactionCustomer = this.buildTransactionCustomer(customer);
            transaction.setCustomer(transactionCustomer);
            return Mono.just(customer);
        })
        .flatMap(customer -> Mono.fromCallable(() -> customerClient.updateCustomer(customer.getId(), customer)))
        .doOnNext(objectResponseEntity -> {
            if (objectResponseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Success update customer: {}", ObjectMapperUtil.writeValueAsString(objectResponseEntity.getBody()));
            }
        })
        .flatMap(unused -> Mono.fromCallable(() -> customerClient.getCustomerById(transaction.getCustomerId())))
        .flatMap(customer -> {
          CustomerBalance balanceToDeduct = CustomerBalance.builder()
              .balance((long) transaction.getSales().getAmount())
              .build();

          return Mono.just(balanceToDeduct);
        })
        .flatMap(customer -> Mono.fromCallable(() -> customerClient.updateCustomerBalance(transaction.getCustomerId(), customer)))
        .doOnNext(objectResponseEntity -> {
          if (objectResponseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Success update customer balance: {}", ObjectMapperUtil.writeValueAsString(objectResponseEntity.getBody()));
          }
        })
        .flatMap(unused -> Mono.fromCallable(() -> customerClient.getCustomerById(transaction.getCustomerId())))
        .flatMap(customer -> {
            TransactionCustomer transactionCustomer = this.buildTransactionCustomer(customer);
            transaction.setCustomer(transactionCustomer);
            return Mono.just(transaction);
        })
        .map(transactions -> TransactionReport.builder()
            .salesId(transactions.getSales().getId())
            .customerId(transactions.getCustomerId())
            .customerName(transactions.getCustomer().getName())
            .quantity(transactions.getQuantity())
            .amount(transactions.getSales().getAmount())
            .productId(transactions.getProductId())
            .productName(transactions.getProduct().getProductName())
            .build()
        )
        .flatMap(transactions -> Mono.fromCallable(() -> transactionRepository.save(transactions)))
        .thenReturn(
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transaction)
        );
  }

  public Mono<ResponseEntity<List<TransactionReport>>> findAllTransactionReport() {
    log.info("Start inquiry transaction report");

    return Mono.fromCallable(() -> transactionRepository.findAll())
        .map(transactions -> {
          log.info("Current transactions: {}", transactions);

          return ResponseEntity
              .status(HttpStatus.OK)
              .body(transactions);
        });

  }

  private TransactionCustomer buildTransactionCustomer(Customer customer) {
    return TransactionCustomer.builder()
        .name(customer.getName())
        .username(customer.getUsername())
        .currentBalance(customer.getBalance())
        .orderId(customer.getOrderId())
        .id(customer.getId())
        .build();
  }
}
