package com.technoaps.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technoaps.company.model.Products;
import com.technoaps.company.repo.ProductsRepository;

@Service
public class ProductsService {
	
	@Autowired
	ProductsRepository productsRepository;
	
	public Products createNewProduct(Products products) {
		return productsRepository.createNewProduct(products);
	}

}
