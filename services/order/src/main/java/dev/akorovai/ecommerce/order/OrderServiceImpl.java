package dev.akorovai.ecommerce.order;

import dev.akorovai.ecommerce.customer.CustomerClient;
import dev.akorovai.ecommerce.exception.BusinessException;
import dev.akorovai.ecommerce.kafka.OrderConfirmation;
import dev.akorovai.ecommerce.kafka.OrderProducer;
import dev.akorovai.ecommerce.orderline.OrderLineRequest;
import dev.akorovai.ecommerce.orderline.OrderLineService;
import dev.akorovai.ecommerce.payment.PaymentClient;
import dev.akorovai.ecommerce.payment.PaymentRequest;
import dev.akorovai.ecommerce.product.ProductClient;
import dev.akorovai.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Override
    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                               .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));
        log.info("CUSTOMER: {}", customer);
        var purchasedProducts = productClient.purchaseProducts(request.products());
        log.info("REQ: {}", request);

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }


    @Override
    public List<OrderResponse> findAll() {
        return this.repository.findAll()
                       .stream()
                       .map(this.mapper::fromOrder)
                       .toList();
    }


    @Override
    public OrderResponse findById(Integer orderId) {
        return this.repository.findById(orderId)
                       .map(this.mapper::fromOrder)
                       .orElseThrow(() -> new EntityNotFoundException(String.format("No order " +
                                                                                            "found with the provided ID: %d", orderId)));
    }
}


