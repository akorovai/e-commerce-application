package dev.akorovai.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer( String id, @NotNull(message = "First Name is mandatory") @NotBlank(message = "First Name is mandatory") @NotEmpty(message = "First Name is mandatory") String firstName, @NotNull(message = "Last Name is mandatory") @NotBlank(message = "Last Name is mandatory") @NotEmpty(message = "Last Name is mandatory") String lastName, @Email(message = "Not correct email format") @NotNull(message = "Email is mandatory") @NotBlank(message = "Email is mandatory") @NotEmpty(message = "Email is mandatory") String email ) {
}
