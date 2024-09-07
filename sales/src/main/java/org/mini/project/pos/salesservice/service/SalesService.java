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
        if (sales.getSalesName() == null || sales.getSalesName().isEmpty()) {
            logger.error("Sales Name cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Sales Name cannot be null or empty");
        } else if (sales.getDescription() == null || sales.getDescription().isEmpty()) {
            logger.error("Description cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Description cannot be null or empty");
        }

        Sales tempSales = salesRepository.findById(sales.getId());

        if (tempSales == null) {
            salesRepository.save(sales);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(sales);
        } else {
            logger.error("Sales with same id already exist");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Sales with same id already exist");
        }
    }

    public ResponseEntity<Object> updateSalesById(long id, Sales sales) {
        if (sales.getSalesName() == null || sales.getSalesName().isEmpty()) {
            logger.error("Sales Name cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Sales Name cannot be null or empty");
        } else if (sales.getDescription() == null || sales.getDescription().isEmpty()) {
            logger.error("Description cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Description cannot be null or empty");
        }

        Sales tempSales = salesRepository.findById(id);

        if (tempSales == null) {
            logger.error("Cannot find sales with id " + id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot find sales with id " + id);
        } else {
            tempSales.setSalesName(sales.getSalesName());
            tempSales.setDescription(sales.getDescription());
            salesRepository.save(tempSales);

            logger.info("Sales updated : " + sales);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Sales updated : " + sales);
        }
    }

    public ResponseEntity<Object> deleteSalesById(long id) {
        Sales tempSales = salesRepository.findById(id);

        if (tempSales == null) {
            logger.error("Cannot find sales with id " + id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot find sales with id " + id);
        } else {
            salesRepository.deleteById(id);

            logger.info("Sales with id " + id + " deleted");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Sales with id " + id + " deleted");
        }
    }
}
