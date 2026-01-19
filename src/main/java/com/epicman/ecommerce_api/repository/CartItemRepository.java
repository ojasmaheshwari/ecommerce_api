package com.epicman.ecommerce_api.repository;

import com.epicman.ecommerce_api.model.CartItemModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends MongoRepository<CartItemModel, String> {
	List<CartItemModel> findByUserId(String userId);

	void deleteByUserId(String userId);
}
