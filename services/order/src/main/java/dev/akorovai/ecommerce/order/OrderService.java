package dev.akorovai.ecommerce.order;

import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {
    Integer createOrder(@Valid OrderRequest orderRequest);

    List<OrderResponse> findAll();

    OrderResponse findById(Integer orderId);
}
