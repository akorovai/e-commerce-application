package dev.akorovai.ecommerce.product;

import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    Integer createProduct(@Valid ProductRequest productRequest);

    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requestList);

    ProductResponse findById(Integer productId);

    List<ProductResponse> findAll();

}
