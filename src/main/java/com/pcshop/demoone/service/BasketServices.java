package com.pcshop.demoone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pcshop.demoone.api.model.Basket;
import com.pcshop.demoone.api.model.Product;
import com.pcshop.demoone.api.model.UserBasket;

@Service
public class BasketServices {

    public static List<Basket> basketList = new ArrayList<>();

    UserService userService = new UserService();
    ProductService productService = new ProductService();

    public Basket getBasket(String userName) {
        for (Basket basket : basketList) {
            if (userName.equals(basket.getUserName())) {
                return basket;
            }
        }
        return null;
    }

    public List<Basket> baskets() {
        return basketList;
    }

    public Basket addBasket(UserBasket userBasket) {
        String username = userService.getUser(userBasket.getUserName());
        Product userproduct = productService.geProduct(userBasket.getProductName());
        if (username != null) {
            if (userproduct != null) {
                if (getBasket(username) != null) {
                    Basket basket = getBasket(username);
                    for (Product product : basket.getProducts()) {
                        if (userBasket.getProductName().equals(product.getName())) {
                            product.setQuantity(product.getQuantity() + 1);
                            return basket;
                        }
                    }
                    List<Product> products = new ArrayList<>(basket.getProducts());
                    productService.geProduct(userBasket.getProductName()).setQuantity(1);
                    products.add(productService.geProduct(userBasket.getProductName()));
                    basket.setProduct(products);
                    return basket;
                } else {
                    userproduct.setQuantity(1);
                    Basket basket = new Basket(userBasket.getUserName(), Arrays.asList(userproduct));
                    basketList.add(basket);
                    return basket;
                }
            }
        }
        return null;
    }

}
