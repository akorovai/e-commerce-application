package dev.akorovai.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomerNullException extends NullPointerException {
    private final String message;
}
