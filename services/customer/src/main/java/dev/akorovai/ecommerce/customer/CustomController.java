package dev.akorovai.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(service.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        service.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Boolean> existsById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/find/{customerId}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteById(@PathVariable String customerId) {
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
