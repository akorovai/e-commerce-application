package dev.akorovai.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(String orderRef, BigDecimal amount, PaymentMethod paymentMethod,
                                  String customerFirstName, String customerLastName,
                                  String customerEmail) { }
