package com.pcshop.demoone.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pcshop.demoone.api.model.Basket;
import com.pcshop.demoone.api.model.UserBasket;
import com.pcshop.demoone.service.BasketServices;

@RestController
@RequestMapping(value = "/PCshop")
public class BasketController {
    private BasketServices basketServices;

    @Autowired
    public BasketController(BasketServices basketServices) {
        this.basketServices = basketServices;
    }

    @GetMapping("/basket")
    public Basket getBasket(@RequestParam String userName) {
        return basketServices.getBasket(userName);
    }

    @PostMapping("/basket")
    public Basket addBasket(@RequestBody UserBasket userBasket) {
        return basketServices.addBasket(userBasket);
    }

    @GetMapping("/baskets")
    public List<Basket> addBasket() {
        return basketServices.baskets();
    }
}
