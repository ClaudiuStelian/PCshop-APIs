package com.pcshop.demoone.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcshop.demoone.service.ProductService;
import com.pcshop.demoone.api.model.Product;

@RestController
@RequestMapping(value = "/PCshop")
public class ProdctController {
    private ProductService productService;

    public ProdctController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/productid/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<?> getProduct(@PathVariable String name) {
        return productService.getProduct(name);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/product/register")
    public ResponseEntity<?> registerProduct(@RequestBody Product productInput) {
        return productService.registerProduct(productInput);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    @PatchMapping("/product/update")
    public ResponseEntity<?> updateProduct(@RequestBody(required = false) Product product) {
        if (product == null) {
            return ResponseEntity.badRequest().body("Bad request please see the API specs");
        }
        return productService.updateProduct(product);
    }
}
