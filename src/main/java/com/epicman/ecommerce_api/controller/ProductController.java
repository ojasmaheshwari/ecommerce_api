package com.epicman.ecommerce_api.controller;

import com.epicman.ecommerce_api.dto.ProductCreationDTO;
import com.epicman.ecommerce_api.model.ProductModel;
import com.epicman.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	// Create a new product
	@PostMapping
	public ResponseEntity<ProductModel> createProduct(@RequestBody ProductCreationDTO dto) {
		// Save the product to MongoDB
		ProductModel product = new ProductModel(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
		ProductModel savedProduct = productRepository.save(product);
		return ResponseEntity.ok(savedProduct);
	}

	// Get all products
	@GetMapping
	public ResponseEntity<List<ProductModel>> getAllProducts() {
		List<ProductModel> products = productRepository.findAll();
		return ResponseEntity.ok(products);
	}
}
