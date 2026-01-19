package com.epicman.ecommerce_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "orders")
public class OrderModel {

	@Id
	private String id;
	private String userId; // Reference to UserModel
	private Double totalAmount;
	private String status; // CREATED, PAID, FAILED, CANCELLED
	private Instant createdAt;

	// Constructors
	public OrderModel() {
	}

	public OrderModel(String id, String userId, Double totalAmount, String status, Instant createdAt) {
		this.id = id;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.createdAt = createdAt;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
