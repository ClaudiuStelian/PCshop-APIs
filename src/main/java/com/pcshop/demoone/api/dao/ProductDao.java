package com.pcshop.demoone.api.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pcshop.demoone.api.model.Product;

public interface ProductDao extends CrudRepository<Product, Integer> {

    Optional<Product> findByName(String name);

}
