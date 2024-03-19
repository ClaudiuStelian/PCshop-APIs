package com.pcshop.demoone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pcshop.demoone.api.dao.ProductDao;
import com.pcshop.demoone.api.dao.UserDao;
import com.pcshop.demoone.api.model.Basket;
import com.pcshop.demoone.api.model.Product;
import com.pcshop.demoone.api.model.ProductsRequest;
import com.pcshop.demoone.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class BasketService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    public ResponseEntity<Object> addItemsToBasket(ProductsRequest productsRequest) {
        if (productsRequest.getQuantity() == 0) {
            productsRequest.setQuantity(1);
        }
        Basket basket = new Basket();
        basket.setName(productsRequest.getProductName());
        basket.setQuantity(productsRequest.getQuantity());

        Optional<Product> productOptional = productDao.findByName(productsRequest.getProductName());
        if (userDao.findByEmail(productsRequest.getEmail()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("User %s not found", productsRequest.getEmail()));
        } else if (productOptional.isPresent()) {
            Product product = productOptional.get();
            basket.setPrice(Double.parseDouble(product.getPrice().replaceAll(" GBP", "")));

            User existingUser = userDao.findByEmail(productsRequest.getEmail());

            if (existingUser.getBasket() == null) {
                existingUser.getBasket().add(basket);
            } else {
                List<Basket> existingBasket = existingUser.getBasket();
                Optional<Product> existingProductOptional = productDao.findByName(productsRequest.getProductName());
                if (existingProductOptional.isPresent()) {
                    Product existingProduct = existingProductOptional.get();
                    Optional<Basket> existingBasketOptional = existingBasket.stream()
                            .filter(b -> b.getName().equals(existingProduct.getName()))
                            .findFirst();

                    if (existingBasketOptional.isPresent()) {
                        Basket existingBasketItem = existingBasketOptional.get();
                        int updatedQuantity = existingBasketItem.getQuantity() + productsRequest.getQuantity();
                        existingBasketItem.setQuantity(updatedQuantity);
                    } else {
                        existingBasket.add(basket);
                    }
                }
                existingUser.setBasket(existingBasket);
            }
            return ResponseEntity.ok().body(userDao.save(existingUser));

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");
        }
    }

    public ResponseEntity<Object> getBasket(String email) {
        List<Basket> basket = userDao.findByEmail(email).getBasket();
        ResponsData responsData = new ResponsData(email, basket);
        return ResponseEntity.ok().body(responsData);
    }

    public ResponseEntity<Object> getBaskets() {
        List<User> responsUsers = (List<User>) userDao.findAll();
        List<ResponsData> responsDatas = new ArrayList<>();
        for (User user : responsUsers) {
            ResponsData responsData = new ResponsData(user.getEmail(), user.getBasket());
            responsDatas.add(responsData);
        }
        return ResponseEntity.ok().body(responsDatas);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponsData {
        private String email;
        private List<Basket> basket;
    }

}
