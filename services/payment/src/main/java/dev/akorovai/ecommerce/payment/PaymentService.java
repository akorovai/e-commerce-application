package dev.akorovai.ecommerce.payment;

import jakarta.validation.Valid;

public interface PaymentService {
    Integer createPayment(@Valid PaymentRequest request);
}
