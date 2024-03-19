package com.pcshop.demoone.api.controller;

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
import com.pcshop.demoone.api.dao.ProductDao;
import com.pcshop.demoone.api.model.Product;
import com.pcshop.demoone.service.BasketService;
import com.pcshop.demoone.service.ProductService;
import com.pcshop.demoone.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ProductObj {
        private int id;
        private String name;
        private double price;
        private int quantity;
}

@WebMvcTest
public class ProdctControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private ProductDao productDao;

        @MockBean
        private ProductService productService;

        @MockBean
        private BasketService basketService;

        @MockBean
        private UserService userService;

        private ProductObj productObj = new ProductObj(1, "CRISPS", 12.0, 100);
        private Product product = new Product(1, "CRISPS", 12.0, 100);
        private List<Product> products = Arrays.asList(
                        new Product(1, "CRISPS", 12.0, 100),
                        new Product(1, "CRISPS", 12.0, 100));

        @SuppressWarnings("null")
        @Test
        void testDeleteProduct() throws JsonProcessingException, Exception {
                Mockito.when(productService.deleteProduct(1))
                                .thenReturn(ResponseEntity.ok()
                                                .body("Product CRISPS with ID 1 has been deleted successfully"));
                mockMvc.perform(MockMvcRequestBuilders
                                .delete("/PCshop/product/delete/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content()
                                                .string("Product CRISPS with ID 1 has been deleted successfully"));
        }

        @SuppressWarnings("null")
        @Test
        void testGetProductByName() throws JsonProcessingException, Exception {
                Mockito.when(productService.getProductByName("CRISPS"))
                                .thenReturn(ResponseEntity.ok().body(product));
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/PCshop/product/CRISPS")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(product)));
        }

        @SuppressWarnings("null")
        @Test
        void testGetProductById() throws JsonProcessingException, Exception {
                Mockito.when(productService.getProductById(1))
                                .thenReturn(ResponseEntity.ok().body(product));
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/PCshop/productid/1")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(product)));
        }

        @SuppressWarnings("null")
        @Test
        void testGetProducts() throws JsonProcessingException, Exception {
                Mockito.when(productService.getProducts())
                                .thenReturn(ResponseEntity.ok().body(products));
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/PCshop/products")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(products)));
        }

        @SuppressWarnings("null")
        @Test
        void testRegisterProduct() throws JsonProcessingException, Exception {
                Mockito.when(productService.registerProduct(product))
                                .thenReturn(ResponseEntity.ok().body(product));
                mockMvc.perform(MockMvcRequestBuilders
                                .post("/PCshop/product/register")
                                .content(objectMapper.writeValueAsString(productObj))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(product)));
        }

        @SuppressWarnings("null")
        @Test
        void testUpdateProduct() throws JsonProcessingException, Exception {
                Mockito.when(productService.updateProduct(product))
                                .thenReturn(ResponseEntity.ok().body(product));
                mockMvc.perform(MockMvcRequestBuilders
                                .patch("/PCshop/product/update")
                                .content(objectMapper.writeValueAsString(productObj))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers
                                                .status().isOk())
                                .andExpect(MockMvcResultMatchers
                                                .content().string(objectMapper.writeValueAsString(product)));
        }
}
