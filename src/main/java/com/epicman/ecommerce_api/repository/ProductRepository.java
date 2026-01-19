package com.epicman.ecommerce_api.repository;

import com.epicman.ecommerce_api.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String> {

	// Find products by name
	List<ProductModel> findByName(String name);

	// Find products with price less than a certain value
	List<ProductModel> findByPriceLessThan(Double price);

	// Find products with stock greater than a certain value
	List<ProductModel> findByStockGreaterThan(Integer stock);

}
