package org.mini.project.pos.salesservice.controller;

import org.mini.project.pos.salesservice.repository.model.Sales;
import org.mini.project.pos.salesservice.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private SalesService salesService;

    @GetMapping()
    public ResponseEntity<Object> findAllSales() {
        return salesService.findAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findSalesById(@PathVariable("id") long id) {
        return salesService.findSalesById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> addSales(@RequestBody Sales sales) {
        return salesService.addSales(sales);
    }
}
