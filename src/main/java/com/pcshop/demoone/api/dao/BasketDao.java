package com.pcshop.demoone.api.dao;

import org.springframework.data.repository.CrudRepository;

import com.pcshop.demoone.api.model.Basket;

public interface BasketDao extends CrudRepository<Basket, Integer> {

}
