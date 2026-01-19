package com.epicman.ecommerce_api.repository;

import com.epicman.ecommerce_api.model.OrderModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String> {
	List<OrderModel> findByUserId(String userId);
}
