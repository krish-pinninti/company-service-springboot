package com.technoaps.company.repo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.technoaps.company.model.Products;

@Repository
public class ProductsRepository {

	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Products createNewProduct(Products product) {
		entityManager.getTransaction().begin();
		entityManager.persist(product);
		entityManager.flush();
		entityManager.getTransaction().commit();		
		return product;
	}
}
