package dev.akorovai.ecommerce.order;

import dev.akorovai.ecommerce.customer.CustomerClient;
import dev.akorovai.ecommerce.exception.BusinessException;
import dev.akorovai.ecommerce.orderline.OrderLineRequest;
import dev.akorovai.ecommerce.orderline.OrderLineService;
import dev.akorovai.ecommerce.product.ProductClient;
import dev.akorovai.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private OrderLineService orderLineService;
    @Override
    public Integer createOrder(OrderRequest orderRequest) {

        // using FeignClient
        var customer = customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exists with the provided ID"));

        // RestTemplate
        productClient.purchaseProducts(orderRequest.products());

        // TODO: change to Modelmapper
        var order = repository.save(mapper.toOrder(orderRequest));

        for(PurchaseRequest request : orderRequest.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null, order.getId(), request.productId(), request.quantity())
            );
        }








        return 0;
    }
}
