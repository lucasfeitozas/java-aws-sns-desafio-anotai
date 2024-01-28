package com.lucasdev.desafioanotaai.controllers;

import com.lucasdev.desafioanotaai.domain.product.Product;
import com.lucasdev.desafioanotaai.domain.product.ProductDTO;
import com.lucasdev.desafioanotaai.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO productData) {
        Product newProduct = this.service.insert(productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = this.service.getAll();
        return ResponseEntity.ok().body(products);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO productData) {
        Product updatedProduct = this.service.update(id, productData);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") String id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
