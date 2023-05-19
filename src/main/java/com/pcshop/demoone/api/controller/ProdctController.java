package com.pcshop.demoone.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.pcshop.demoone.service.ProductService;
import com.pcshop.demoone.api.model.Product;

@RestController
@RequestMapping(value = "/PCshop")
public class ProdctController {
    private ProductService productService;

    @Autowired
    public ProdctController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public Product getProduct(@RequestParam Integer id) {
        return productService.getProduct(id);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/product/register")
    public String registerProduct(@RequestBody Product product) {
        return productService.registerProduct(product);
    }
}
