package org.mini.project.pos.salesservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mini.project.pos.salesservice.repository.dao.SalesRepository;
import org.mini.project.pos.salesservice.repository.model.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SalesService {
    private final static Logger logger = LogManager.getLogger(SalesService.class);

    @Autowired
    private SalesRepository salesRepository;

    public ResponseEntity<Object> findAllSales() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salesRepository.findAll());
    }

    public ResponseEntity<Object> findSalesById(long id) {
        Sales sales = salesRepository.findById(id);

        if (sales == null) {
            logger.error("Sales not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(sales);
        }
    }

    public ResponseEntity<Object> addSales(Sales sales) {
        if (sales.getPosId() == null || sales.getPosId().isEmpty()) {
            logger.error("Pos id cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Pos id cannot be null or empty");
        } else if (sales.getProductId() == null || sales.getProductId().isEmpty()) {
            logger.error("Product id cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product id cannot be null or empty");
        } else if (sales.getQuantity() <= 0) {
            logger.error("Quantity must be greater than 0");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Quantity must be greater than 0");
        } else if (sales.getAmount() <= 0) {
            logger.error("Amount must be greater than 0");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Amount must be greater than 0");
        }

        salesRepository.save(sales);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sales);
    }
}
