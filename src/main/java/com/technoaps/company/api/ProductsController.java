package com.technoaps.company.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoaps.company.model.Product;
import com.technoaps.company.model.ProductRequest;
import com.technoaps.company.model.Products;
import com.technoaps.company.service.ProductsService;

@RestController
@RequestMapping("/products/")
public class ProductsController {

	
	@Autowired
	ProductsService productsService;

	
	@PostMapping("create")
	public Products createProduct(@RequestBody @Valid ProductRequest productRequest,
			@RequestHeader (value = "Authorization") String token) {
		
		Products products = new Products();
		products.setProductDescription(productRequest.getDesc());
		products.setProductName(productRequest.getName());
		
		return productsService.createNewProduct(products);
	}
	
	@PostMapping("update")
	public Product updateProduct(@RequestBody @Valid Product product,
			@RequestHeader (value = "Authorization") String token) {
		
		
		return new Product();
	}
}
