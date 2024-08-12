package dev.akorovai.ecommerce.notification;

import dev.akorovai.ecommerce.payment.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentNotificationRequest(String orderRef, BigDecimal amount,
                                         PaymentMethod paymentMethod, String customerFirstName,
                                         String customerLastName, String customerEmail) { }
