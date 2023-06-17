package com.pcshop.demoone.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsRequest {

    private String email;
    private String productName;
    private int quantity;
}
