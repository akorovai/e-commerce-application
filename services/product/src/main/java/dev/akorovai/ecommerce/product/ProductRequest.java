package dev.akorovai.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(Integer id,
                             @NotNull(message = "Name is required")
                             String name,
                             @NotNull(message = "Description is required")
                             String description,
                             @Positive(message = "Available Quantity should be positive number")
                             double availableQuantity,
                             @Positive(message = "Price should be positive")
                             BigDecimal price,
                             @NotNull(message = "Product category is required")
                             Integer categoryId) {
}
