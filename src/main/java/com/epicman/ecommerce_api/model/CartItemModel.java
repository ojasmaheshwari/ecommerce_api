package com.epicman.ecommerce_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart_items")
public class CartItemModel {

	@Id
	private String id;
	private String userId; // Reference to UserModel
	private String productId; // Reference to ProductModel
	private Integer quantity;
	private Double price;

	// Constructors
	public CartItemModel() {
	}

	public CartItemModel(String userId, String productId, Integer quantity, Double price) {
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
