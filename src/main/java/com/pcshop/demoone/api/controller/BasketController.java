package com.pcshop.demoone.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcshop.demoone.api.model.ProductsRequest;
import com.pcshop.demoone.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/PCshop")
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping(value = "/basket")
    public ResponseEntity<?> addItemsToBasket(@RequestBody ProductsRequest productsRequest) {
        return basketService.addItemsToBasket(productsRequest);
    }

    @GetMapping(value = "/basket/{email}")
    public ResponseEntity<?> getBasket(@PathVariable String email) {
        return basketService.getBasket(email);
    }

    @GetMapping(value = "/baskets")
    public ResponseEntity<?> getBaskets() {
        return basketService.getBaskets();
    }

}
