package com.epicman.ecommerce_api.repository;

import com.epicman.ecommerce_api.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {
    public UserModel findByUsername(String username);
}
