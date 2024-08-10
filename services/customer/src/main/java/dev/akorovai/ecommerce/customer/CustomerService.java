package dev.akorovai.ecommerce.customer;

import jakarta.validation.Valid;

import java.util.List;

public interface CustomerService {
    String createCustomer(@Valid CustomerRequest customerRequest);

    void updateCustomer(@Valid CustomerRequest customerRequest);

    List<CustomerResponse> findAllCustomers();

    Boolean existsById(String customerId);

    CustomerResponse findById(String customerId);

    void deleteCustomer(String customerId);
}
