package dev.akorovai.ecommerce.order;

import dev.akorovai.ecommerce.customer.CustomerClient;
import dev.akorovai.ecommerce.customer.CustomerResponse;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final ModelMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    @Override
    public Integer createOrder(OrderRequest orderRequest) {

        // using FeignClient
        CustomerResponse customer =
		        customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID"));

        // RestTemplate
        var purchasedProducts = productClient.purchaseProducts(orderRequest.products());


        var order = repository.save(mapper.map(orderRequest, Order.class));

        for (PurchaseRequest request : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null, order.getId(), request.productId(), request.quantity())
            );
        }
        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);


        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts)
        );


        return order.getId();
    }


    @Override
    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(o -> mapper.map(o, OrderResponse.class)).toList();
    }


    @Override
    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId).map(o -> mapper.map(o, OrderResponse.class)).orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID::%d", orderId)));
    }
}


