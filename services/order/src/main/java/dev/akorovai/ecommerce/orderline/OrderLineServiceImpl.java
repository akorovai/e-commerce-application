package dev.akorovai.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderLineServiceImpl implements OrderLineService {
    private final OrderLineRepository repository;
    private final ModelMapper mapper;

    @Override
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = mapper.map(orderLineRequest, OrderLine.class);
        return repository.save(order).getId();
    }

    @Override
    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId).stream().map(o -> mapper.map(o, OrderLineResponse.class)).toList();
    }
}
