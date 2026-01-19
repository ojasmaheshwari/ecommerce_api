package com.epicman.ecommerce_api.repository;

import com.epicman.ecommerce_api.model.CartItemModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<CartItemModel, String> {

	// Get all cart items for a specific user
	List<CartItemModel> findByUserId(String userId);

	// Optional: find a specific product in a user's cart
	CartItemModel findByUserIdAndProductId(String userId, String productId);

	// Optional: delete all items by user ID
	void deleteByUserId(String userId);
}
