package org.mini.project.pos.posservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mini.project.pos.posservice.model.Product;
import org.mini.project.pos.posservice.model.Transaction;
import org.mini.project.pos.posservice.service.PosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/pos")
@RequiredArgsConstructor
public class PosController {

  private final PosService posService;

  @GetMapping
  public Mono<ResponseEntity<List<Product>>> findAllProduct() {
    return posService.productStockInquiry();
  }

  @PostMapping
  public Mono<ResponseEntity<Transaction>> createTransaction(@RequestBody Transaction transaction) {
    return posService.createTransaction(transaction);
  }
}
