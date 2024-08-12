package dev.akorovai.ecommerce.kafka;

import dev.akorovai.ecommerce.customer.CustomerResponse;
import dev.akorovai.ecommerce.order.PaymentMethod;
import dev.akorovai.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(String orderReference, BigDecimal amount, PaymentMethod paymentMethod,
                                CustomerResponse customer, List<PurchaseResponse> products) {
}
