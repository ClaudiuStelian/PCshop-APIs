package com.pcshop.demoone.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pcshop.demoone.api.dao.UserDao;
import com.pcshop.demoone.service.BasketService;
import com.pcshop.demoone.service.ProductService;
import com.pcshop.demoone.service.UserService;

@WebMvcTest
public class UserTest {

    @MockBean
    private UserService useserService;

    @MockBean
    private BasketService basketService;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserDao userDao;

    @Test
    void userObject() {
        List<Basket> baskets = new ArrayList<>();
        baskets.add(new Basket(1, "RTX 4060 GPU", 2000.0, 2));
        baskets.add(new Basket(2, "RTX 4070 GPU", 2500.0, 1));
        var user = new User(2, "Claudiu", LocalDate.parse("1990-01-01"), "myP@ssword123!", "johndos@example.com",
                "male",
                new Card(0, 1234567890123456l, LocalDate.parse("2025-12-31"), 123), baskets);
        assertEquals(2, user.getId());
        assertEquals("RTX 4060 GPU", user.getBasket().get(0).getName());
        assertEquals(1, user.getBasket().get(0).getId());
    }
}
