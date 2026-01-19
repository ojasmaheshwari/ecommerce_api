package com.epicman.ecommerce_api.service;

import com.epicman.ecommerce_api.exception.NotFoundException;
import com.epicman.ecommerce_api.model.CartItemModel;
import com.epicman.ecommerce_api.model.ProductModel;
import com.epicman.ecommerce_api.repository.CartRepository;
import com.epicman.ecommerce_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	public CartItemModel addItem(CartItemModel cartItem) {
		// Optional: validate product exists
		ProductModel product = productRepository.findById(cartItem.getProductId())
				.orElseThrow(() -> new NotFoundException("Product not found"));
		return cartRepository.save(cartItem);
	}

	public List<CartItemModel> getUserCart(String userId) {
		List<CartItemModel> items = cartRepository.findByUserId(userId);
		// Populate product details
		for (CartItemModel item : items) {
			ProductModel product = productRepository.findById(item.getProductId()).orElse(null);
			item.setProductId(product.getId());
		}
		return items;
	}

	public void clearCart(String userId) {
		List<CartItemModel> items = cartRepository.findByUserId(userId);
		cartRepository.deleteAll(items);
	}
}
