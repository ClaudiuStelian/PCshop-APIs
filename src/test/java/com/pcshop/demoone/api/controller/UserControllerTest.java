package com.pcshop.demoone.api.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcshop.demoone.api.dao.UserDao;
import com.pcshop.demoone.api.model.Card;
import com.pcshop.demoone.api.model.User;
import com.pcshop.demoone.service.BasketService;
import com.pcshop.demoone.service.ProductService;
import com.pcshop.demoone.service.UserService;

@WebMvcTest
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private UserService userService;

        @MockBean
        private UserDao userDao;

        @MockBean
        private BasketService basketService;

        @MockBean
        private ProductService productService;

        private List<User> userList = Arrays.asList(
                        new User(1, "John", LocalDate.now(), "password", "john@example.com", "male", null, null),
                        new User(2, "Jane", LocalDate.now(), "password", "jane@example.com", "female", null, null));

        private Card card = new Card(1, 1234567890123456L, LocalDate.parse("2025-12-31"), 123);
        private User user = new User(1, "John Doe", LocalDate.parse("1990-01-01"), "myP@ssword123!",
                        "Loomee@example.com", "male", card, null);

        @SuppressWarnings("null")
        @Test
        void testDeleteUser() throws Exception {
                Mockito.when(userService.deleteUser(1, "myP@ssword123!"))
                                .thenReturn(ResponseEntity.ok()
                                                .body("The password header does not match to the registered ID 1"));
                mockMvc.perform(MockMvcRequestBuilders
                                .delete("/PCshop/user/delete/1")
                                .header("password", "myP@ssword123!")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content()
                                                .string("The password header does not match to the registered ID 1"));
        }

        @SuppressWarnings("null")
        @Test
        void testFindUser() throws JsonProcessingException, Exception {
                Mockito.when(userService.getUser(1))
                                .thenReturn(ResponseEntity.ok().body(user));
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/PCshop/userid/1"))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(user)));
        }

        @SuppressWarnings("null")
        @Test
        void testFindUserByEMail() throws JsonProcessingException, Exception {
                Mockito.when(userService.getUserByEmail("john@example.com"))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(user));
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/PCshop/user/john@example.com"))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(user)));
        }

        @SuppressWarnings("null")
        @Test
        void testGetUserList() throws JsonProcessingException, Exception {
                Mockito.when(userService.getAllUserList())
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(userList));
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/PCshop/users"))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(userList)));
        }

        @SuppressWarnings("null")
        @Test
        void testRegisterUser() throws JsonProcessingException, Exception {
                Mockito.when(userService.registerUser(user))
                                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(user));
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/PCshop/user/register")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isCreated())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(user)));

        }

        @SuppressWarnings("null")
        @Test
        void testUpdateUser() throws JsonProcessingException, Exception {
                Mockito.when(userService.updateUser(user))
                                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(user));
                mockMvc.perform(MockMvcRequestBuilders
                                .patch("/PCshop/user/patch")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(user)));

        }
}
