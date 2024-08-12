package dev.akorovai.ecommerce.payment;

import dev.akorovai.ecommerce.customer.CustomerResponse;
import dev.akorovai.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
		BigDecimal amount,
		PaymentMethod paymentMethod,
		Integer orderId,
		String orderReference,
		CustomerResponse customer
) {
}

