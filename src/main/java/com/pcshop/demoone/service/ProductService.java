package com.pcshop.demoone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pcshop.demoone.api.dao.ProductDao;
import com.pcshop.demoone.api.model.Product;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public ResponseEntity<?> getProduct(int id) {
        if ((id > 0) && productDao.findById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productDao.findById(id));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Product id %s was not found", id));
    }

    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productDao.findAll());
    }

    public ResponseEntity<?> registerProduct(Product product) {
        if (productDao.findByName(product.getName()).isPresent() || (product == null) || (product.getName() == null)
                || (product.getPrice().equals("0.0 GBP"))
                || (product.getQuantity() <= 0)
                || productDao.findByName(product.getName()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request please see the API specs");
        } else
            return ResponseEntity.status(HttpStatus.OK).body(productDao.save(product));
    }

    public ResponseEntity<?> getProduct(String name) {
        if (productDao.findByName(name).orElse(null) != null) {
            return ResponseEntity.ok().body(productDao.findByName(name));
        }
        return ResponseEntity.badRequest().body("Bad request please see the API specs");
    }

    public ResponseEntity<?> deleteProduct(int id) {
        if (productDao.findById(id).isPresent()) {
            productDao.deleteById(id);
            return ResponseEntity.ok()
                    .body(String.format("Product %s with ID %d has been deleted successfully",
                            productDao.findById(id).get().getName(), id));
        } else
            return ResponseEntity.badRequest().body("Bad request please see the API specs");
    }

    public ResponseEntity<?> updateProduct(Product product) {
        if (product.getName() == null) {
            return ResponseEntity.badRequest().body("Product name is not declared");
        } else if (!(productDao.findByName(product.getName()).isPresent()) ||
                (product.getPrice().isEmpty())
                || (product.getQuantity() <= 0)) {
            return ResponseEntity.badRequest().body("Bad request please see the API specs");
        } else {
            Optional<Product> existingProduct = productDao.findByName(product.getName());
            existingProduct.get().setName(product.getName());
            existingProduct.get().setPrice(Double.parseDouble(product.getPrice().replaceAll(" GBP", "")));
            existingProduct.get().setQuantity(product.getQuantity());
            return ResponseEntity.ok().body(productDao.save(existingProduct.get()));
        }
    }
}
