package com.pcshop.demoone.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pcshop.demoone.api.model.Product;

@Service
public class ProductService {

    public static List<Product> productList = new ArrayList<>();

    public Product getProduct(Integer id) {
        for (Product product : productList) {
            if (id == product.getId()) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return productList;
    }

    public String registerProduct(Product product) {
        productList.add(product);
        return "Product registered successfully";
    }

    public Product geProduct(String productName) {
        for (Product product : productList) {
            if (productName.equals(product.getName())) {
                return product;
            }
        }
        return null;
    }
}
