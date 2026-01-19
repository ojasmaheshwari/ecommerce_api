package com.epicman.ecommerce_api.controller;

import com.epicman.ecommerce_api.model.CartItemModel;
import com.epicman.ecommerce_api.model.ProductModel;
import com.epicman.ecommerce_api.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// Add item to cart
	@PostMapping("/add")
	public ResponseEntity<CartItemModel> addToCart(@RequestBody CartItemModel cartItem) {
		CartItemModel savedItem = cartService.addItem(cartItem);
		return ResponseEntity.ok(savedItem);
	}

	// Get user's cart
	@GetMapping("/")
	public ResponseEntity<List<CartItemModel>> getCart(HttpServletRequest request) {
		String userId = (String) request.getAttribute("userId");
		List<CartItemModel> cartItems = cartService.getUserCart(userId);
		return ResponseEntity.ok(cartItems);
	}

	// Clear user's cart
	@DeleteMapping("/clear")
	public ResponseEntity<?> clearCart(HttpServletRequest request) {
		String userId = (String) request.getAttribute("userId");
		cartService.clearCart(userId);
		return ResponseEntity.ok().body(
				java.util.Collections.singletonMap("message", "Cart cleared successfully"));
	}
}
