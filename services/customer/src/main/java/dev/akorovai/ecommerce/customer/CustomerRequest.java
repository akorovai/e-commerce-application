package dev.akorovai.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(String id,
                              @NotNull(message = "First Name is required")
                              String firstName,
                              @NotNull(message = "Last Name is required")
                              String lastName,
                              @Email(message = "Email is not valid")
                              String email,
                              Address address) {
}
