package dev.akorovai.ecommerce.kafka.order;

import dev.akorovai.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
		String orderRef,
		BigDecimal totalAmount,
		PaymentMethod paymentMethod,
		Customer customer,
		List<Product> products
		) {

}
