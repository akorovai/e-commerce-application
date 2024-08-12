package dev.akorovai.ecommerce.payment;

import dev.akorovai.ecommerce.notification.NotificationProducer;
import dev.akorovai.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository repository;
	private final ModelMapper modelMapper;
	private final NotificationProducer notificationProducer;

	@Override
	public Integer createPayment( PaymentRequest request ) {
		var payment = repository.save(modelMapper.map(request, Payment.class));

		var pnr = PaymentNotificationRequest.builder().orderRef(request.orderReference()).amount(request.amount()).paymentMethod(request.paymentMethod()).customerFirstName(request.customer().firstName()).customerLastName(request.customer().lastName()).customerEmail(request.customer().email()).build();

		notificationProducer.sendNotification(pnr);

		return payment.getId();
	}
}
