package dev.akorovai.ecommerce.customer;

import dev.akorovai.ecommerce.exception.CustomerNotFoundException;
import dev.akorovai.ecommerce.exception.CustomerNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    @Override
    public String createCustomer(CustomerRequest customerRequest) {
        validateCustomerRequest(customerRequest);
        var customer = this.repository.save(mapper.toCustomer(customerRequest));
        return customer.getId();
    }

    @Override
    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = repository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot find customer with id %s", customerRequest.id())));

        mergeCustomerDetails(customer, customerRequest);
        repository.save(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return  this.repository.findAll()
                        .stream()
                        .map(this.mapper::fromCustomer)
                        .collect(Collectors.toList());
    }

    @Override
    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    @Override
    public CustomerResponse findById(String id) {
        return this.repository.findById(id)
                       .map(mapper::fromCustomer)
                       .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
    }

    @Override
    public void deleteCustomer(String customerId) {
        repository.findById(customerId).orElseThrow(() -> new CustomerNullException(String.format("Cannot find customer with id %s", customerId)));
        repository.deleteById(customerId);
    }

    private void validateCustomerRequest(CustomerRequest customerRequest) {
        if (Objects.isNull(customerRequest)) {
            throw new CustomerNullException("Customer cannot be null");
        }
    }

    private void mergeCustomerDetails(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.hasText(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.hasText(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.hasText(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if (Objects.nonNull(customerRequest.address())) {
            customer.setAddress(customerRequest.address());
        }
    }
}
