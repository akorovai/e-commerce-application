package dev.akorovai.ecommerce.order;

import dev.akorovai.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(Integer id, String reference,
                           @Positive(message = "Amount should be positive number") BigDecimal amount,
                           @NotNull(message = "Payment method is mandatory") PaymentMethod paymentMethod,
                           @NotNull(message = "Customer ID is mandatory")
                           @NotEmpty(message = "Customer ID is mandatory")
                           @NotBlank(message = "Customer ID is mandatory")
                           String customerId,
                           @NotEmpty(message = "You should purchase one product at least")
                           List<PurchaseRequest> products) {
}
