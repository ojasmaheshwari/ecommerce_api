package com.epicman.ecommerce_api.controller;

import com.epicman.ecommerce_api.model.PaymentModel;
import com.epicman.ecommerce_api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.epicman.ecommerce_api.webhook.PaymentWebhookController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentRepository paymentRepository;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private final RestTemplate restTemplate = new RestTemplate();

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

		// Schedule mock webhook call after 3 seconds
		scheduler.schedule(() -> callWebhook(payment), 3, TimeUnit.SECONDS);

		return ResponseEntity.ok(payment);
	}

	// Simulate calling the webhook endpoint
	private void callWebhook(PaymentModel payment) {
		String webhookUrl = "http://localhost:8080/api/payments/webhook"; // Adjust host/port as needed

		PaymentWebhookController.WebhookRequest webhookRequest = new PaymentWebhookController.WebhookRequest();
		webhookRequest.setPaymentId(payment.getId());
		webhookRequest.setStatus("SUCCESS");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaymentWebhookController.WebhookRequest> entity = new HttpEntity<>(webhookRequest, headers);

		try {
			restTemplate.postForEntity(webhookUrl, entity, String.class);
			System.out.println("Mock webhook called for payment " + payment.getPaymentId());
		} catch (Exception e) {
			System.err.println("Failed to call webhook for payment " + payment.getPaymentId() + ": " + e.getMessage());
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
