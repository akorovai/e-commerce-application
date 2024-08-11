package dev.akorovai.ecommerce.order;

import jakarta.validation.Valid;

public interface OrderService {
    Integer createOrder(@Valid OrderRequest orderRequest);
}
