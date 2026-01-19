package com.epicman.ecommerce_api.controller;

import com.epicman.ecommerce_api.model.PaymentModel;
import com.epicman.ecommerce_api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentRepository paymentRepository;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@PostMapping("/create")
	public ResponseEntity<PaymentModel> createPayment(@RequestBody PaymentRequest request) {
		// Create a mock payment
		PaymentModel payment = new PaymentModel();
		payment.setId(UUID.randomUUID().toString());
		payment.setPaymentId("pay_mock" + UUID.randomUUID().toString().substring(0, 6));
		payment.setOrderId(request.getOrderId());
		payment.setAmount(request.getAmount());
		payment.setStatus("PENDING");
		payment.setCreatedAt(Instant.now());

		paymentRepository.save(payment);

		// Simulate webhook call after 3 seconds
		scheduler.schedule(() -> mockWebhook(payment.getId()), 3, TimeUnit.SECONDS);

		return ResponseEntity.ok(payment);
	}

	// Mock webhook simulation
	private void mockWebhook(String paymentId) {
		PaymentModel payment = paymentRepository.findById(paymentId).orElse(null);
		if (payment != null) {
			payment.setStatus("SUCCESS"); // Update status
			paymentRepository.save(payment);
			System.out.println("Webhook called: Payment " + paymentId + " marked SUCCESS");
		}
	}

	// Request DTO
	public static class PaymentRequest {
		private String orderId;
		private Double amount;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}
	}
}
