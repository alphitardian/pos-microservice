package org.mini.project.pos.productservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mini.project.pos.productservice.repository.dao.ProductRepository;
import org.mini.project.pos.productservice.repository.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final static Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Object> findAllProduct() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productRepository.findAll());
    }

    public ResponseEntity<Object> findProductById(long id) {
        Product product = productRepository.findById(id);

        if (product == null) {
            logger.error("Product not found");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(product);
        }
    }

    public ResponseEntity<Object> addProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            logger.error("Product Name cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product Name cannot be null or empty");
        } else if (product.getDescription() == null || product.getDescription().isEmpty()) {
            logger.error("Description cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Description cannot be null or empty");
        }

        Product tempProduct = productRepository.findById(product.getId());

        if (tempProduct == null) {
            productRepository.save(product);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(product);
        } else {
            logger.error("Product with same id already exist");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product with same id already exist");
        }
    }

    public ResponseEntity<Object> updateProductById(long id, Product product) {
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            logger.error("Product Name cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product Name cannot be null or empty");
        } else if (product.getDescription() == null || product.getDescription().isEmpty()) {
            logger.error("Description cannot be null or empty");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Description cannot be null or empty");
        }

        Product tempProduct = productRepository.findById(id);

        if (tempProduct == null) {
            logger.error("Cannot find product with id " + id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot find product with id " + id);
        } else {
            tempProduct.setProductName(product.getProductName());
            tempProduct.setDescription(product.getDescription());
            tempProduct.setQuantity(product.getQuantity());
            tempProduct.setPrice(product.getPrice());
            productRepository.save(tempProduct);

            logger.info("Product updated : " + tempProduct);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tempProduct);
        }
    }

    public ResponseEntity<Object> deleteProductById(long id) {
        Product tempProduct = productRepository.findById(id);

        if (tempProduct == null) {
            logger.error("Cannot find product with id " + id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot find product with id " + id);
        } else {
            productRepository.deleteById(id);

            logger.info("Product with id " + id + " deleted");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Product with id " + id + " deleted");
        }
    }

    public ResponseEntity<Object> subtractProductById(long id, int number) {
        if (number <= 0) {
            logger.error("Number must be greater than 0");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Number must be greater than 0");
        }

        Product tempProduct = productRepository.findById(id);

        if (tempProduct == null) {
            logger.error("Cannot find product with id " + id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot find product with id " + id);
        } else {
            if(number > tempProduct.getQuantity()) {
                logger.error("Number greater than stock");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Number greater than stock");
            }

            tempProduct.setQuantity(tempProduct.getQuantity() - number);
            productRepository.save(tempProduct);

            logger.info("Product updated : " + tempProduct);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tempProduct);
        }
    }

    public ResponseEntity<Object> addProductById(long id, int number) {
        if (number <= 0) {
            logger.error("Number must be greater than 0");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Number must be greater than 0");
        }

        Product tempProduct = productRepository.findById(id);

        if (tempProduct == null) {
            logger.error("Cannot find product with id " + id);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot find product with id " + id);
        } else {
            tempProduct.setQuantity(tempProduct.getQuantity() + number);
            productRepository.save(tempProduct);

            logger.info("Product updated : " + tempProduct);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(tempProduct);
        }
    }
}
