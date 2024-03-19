package com.pcshop.demoone.api.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcshop.demoone.api.dao.BasketDao;
import com.pcshop.demoone.api.dao.ProductDao;
import com.pcshop.demoone.api.dao.UserDao;
import com.pcshop.demoone.api.model.Basket;
import com.pcshop.demoone.api.model.Card;
import com.pcshop.demoone.api.model.ProductsRequest;
import com.pcshop.demoone.api.model.User;
import com.pcshop.demoone.service.BasketService;
import com.pcshop.demoone.service.BasketService.ResponsData;
import com.pcshop.demoone.service.ProductService;
import com.pcshop.demoone.service.UserService;

@WebMvcTest
public class BasketControllerTest {

    @MockBean
    private BasketDao BasketDao;

    @MockBean
    private UserDao userDao;

    @MockBean
    private ProductDao productDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BasketService basketService;

    @MockBean
    private UserService UserService;

    @MockBean
    private ProductService ProductService;

    private List<Basket> baskets = Arrays.asList(
            new Basket(1, "CRISPS", 12.0, 2));
    private Card card = new Card(1, 1234567890123456L, LocalDate.parse("2025-12-31"), 123);
    private User user = new User(1, "John Doe", LocalDate.parse("1990-01-01"), "myP@ssword123!",
            "Loomee@example.com", "male", card, baskets);

    private ProductsRequest productsRequest = new ProductsRequest("Loomee@example.com", "CRISPS", 2);

    @SuppressWarnings("null")
    @Test
    void testGetBasket() throws JsonProcessingException, Exception {
        Mockito.when(basketService.getBasket("Loomee@example.com"))
                .thenReturn(ResponseEntity.ok().body(new ResponsData("Loomee@example.com", baskets)));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/PCshop/basket/Loomee@example.com"))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().string(objectMapper.writeValueAsString(
                                new ResponsData("Loomee@example.com", baskets))));
    }

    @SuppressWarnings("null")
    @Test
    void testAddItemsToBasket() throws JsonProcessingException, Exception {
        Mockito.when(basketService.addItemsToBasket(productsRequest))
                .thenReturn(ResponseEntity.ok().body(user));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/PCshop/basket")
                .content(objectMapper.writeValueAsString(productsRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content().string(objectMapper.writeValueAsString(user)));
    }
}
