package dev.akorovai.ecommerce.payment;

import dev.akorovai.ecommerce.notification.NotificationProducer;
import dev.akorovai.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository repository;
	private final PaymentMapper mapper;
	private final NotificationProducer notificationProducer;

	@Override
	public Integer createPayment(PaymentRequest request) {
		Payment p = this.mapper.toPayment(request);
		var payment = this.repository.save(p);

		this.notificationProducer.sendNotification(
				new PaymentNotificationRequest(
						request.orderReference(),
						request.amount(),
						request.paymentMethod(),
						request.customer().firstName(),
						request.customer().lastName(),
						request.customer().email()
				)
		);
		return payment.getId();
	}
}
