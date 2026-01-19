package com.epicman.ecommerce_api.controller;

import com.epicman.ecommerce_api.model.CartItemModel;
import com.epicman.ecommerce_api.model.OrderModel;
import com.epicman.ecommerce_api.model.OrderItemModel;
import com.epicman.ecommerce_api.model.PaymentModel;
import com.epicman.ecommerce_api.repository.CartItemRepository;
import com.epicman.ecommerce_api.repository.OrderRepository;
import com.epicman.ecommerce_api.repository.PaymentRepository;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	// Create order from cart
	@PostMapping
	public OrderModel createOrder(@RequestBody CreateOrderRequest request) {
		String userId = request.getUserId();

		// Fetch cart items
		List<CartItemModel> cartItems = cartItemRepository.findByUserId(userId);

		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty for user: " + userId);
		}

		// Convert cart items to order items and calculate total
		List<OrderItemModel> orderItems = cartItems.stream().map(ci -> {
			OrderItemModel oi = new OrderItemModel();
			oi.setProductId(ci.getProductId());
			oi.setQuantity(ci.getQuantity());
			oi.setPrice(ci.getPrice());
			return oi;
		}).collect(Collectors.toList());

		double totalAmount = orderItems.stream()
				.mapToDouble(item -> item.getPrice() * item.getQuantity())
				.sum();

		// Create order
		OrderModel order = new OrderModel();
		order.setUserId(userId);
		order.setStatus("CREATED");
		order.setItems(orderItems);
		order.setTotalAmount(totalAmount);

		orderRepository.save(order);

		// Optionally clear cart after order
		cartItemRepository.deleteByUserId(userId);

		return order;
	}

	// Get all orders for a user
	@GetMapping
	public List<OrderModel> getOrders(HttpServletRequest request) {
		String userId = (String) request.getAttribute("userId");
		List<OrderModel> orders = orderRepository.findByUserId(userId);

		// Include payment info if exists
		orders.forEach(order -> {
			PaymentModel payment = paymentRepository.findByOrderId(order.getId());
			order.setPayment(payment);
		});

		return orders;
	}

	// DTO for order creation
	public static class CreateOrderRequest {
		private String userId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	}
}
