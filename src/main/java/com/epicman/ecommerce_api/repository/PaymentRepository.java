package com.epicman.ecommerce_api.repository;

import com.epicman.ecommerce_api.model.PaymentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentModel, String> {
	PaymentModel findByOrderId(String orderId);
}
