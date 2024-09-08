package org.mini.project.pos.productservice.controller;

import org.mini.project.pos.productservice.repository.model.Product;
import org.mini.project.pos.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<Object> findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable("id") long id) {
        return productService.findProductById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable("id") long id,
                                                    @RequestBody Product product) {
        return productService.updateProductById(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable("id") long id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/subtract/{id}/{number}")
    public ResponseEntity<Object> subtractProductById(@PathVariable("id") long id,
                                                      @PathVariable("number") int number) {
        return productService.subtractProductById(id, number);
    }

    @PutMapping("/add/{id}/{number}")
    public ResponseEntity<Object> addProductById(@PathVariable("id") long id,
                                                 @PathVariable("number") int number) {
        return productService.addProductById(id, number);
    }
}
