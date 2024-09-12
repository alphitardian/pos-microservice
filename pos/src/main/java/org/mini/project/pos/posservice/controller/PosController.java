package org.mini.project.pos.posservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mini.project.pos.posservice.model.Product;
import org.mini.project.pos.posservice.model.transaction.Transaction;
import org.mini.project.pos.posservice.model.transaction.TransactionReport;
import org.mini.project.pos.posservice.service.PosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/pos")
@RequiredArgsConstructor
public class PosController {

  private final PosService posService;

  @GetMapping("/products")
  public Mono<ResponseEntity<List<Product>>> findAllProduct() {
    return posService.productStockInquiry();
  }

  @PostMapping("/transactions")
  public Mono<ResponseEntity<Transaction>> createTransaction(@RequestBody Transaction transaction) {
    return posService.createTransaction(transaction);
  }

  @GetMapping("/transactions")
  public Mono<ResponseEntity<List<TransactionReport>>> findAllTransactionReport() {
    return posService.findAllTransactionReport();
  }
}
