package com.epicman.ecommerce_api.webhook;

import com.epicman.ecommerce_api.model.PaymentModel;
import com.epicman.ecommerce_api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments/webhook")
public class PaymentWebhookController {

	@Autowired
	private PaymentRepository paymentRepository;

	// Endpoint called by payment provider (mocked)
	@PostMapping
	public ResponseEntity<String> handleWebhook(@RequestBody WebhookRequest request) {
		Optional<PaymentModel> optionalPayment = paymentRepository.findById(request.getPaymentId());

		if (optionalPayment.isEmpty()) {
			return ResponseEntity.badRequest().body("Payment not found");
		}

		PaymentModel payment = optionalPayment.get();
		payment.setStatus(request.getStatus());
		paymentRepository.save(payment);

		System.out
				.println("Webhook received: Payment " + payment.getPaymentId() + " status updated to " + payment.getStatus());
		return ResponseEntity.ok("Webhook processed successfully");
	}

	// DTO for webhook request
	public static class WebhookRequest {
		private String paymentId;
		private String status; // SUCCESS, FAILED, etc.

		public String getPaymentId() {
			return paymentId;
		}

		public void setPaymentId(String paymentId) {
			this.paymentId = paymentId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}
}
