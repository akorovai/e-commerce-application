package dev.akorovai.ecommerce.kafka;

import dev.akorovai.ecommerce.email.EmailService;
import dev.akorovai.ecommerce.kafka.order.OrderConfirmation;
import dev.akorovai.ecommerce.kafka.payment.PaymentConfirmation;
import dev.akorovai.ecommerce.notification.Notification;
import dev.akorovai.ecommerce.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.akorovai.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static dev.akorovai.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
	private final NotificationRepository repository;

	private final EmailService emailService;

	@KafkaListener(topics = "payment-topic")
	public void consumePaymentSuccessNotification( PaymentConfirmation paymentConfirmation ) throws MessagingException {
		log.info("Received Payment Confirmation: {}", paymentConfirmation);

		repository.save(Notification.builder().type(PAYMENT_CONFIRMATION).notificationDateTime(LocalDateTime.now()).paymentConfirmation(paymentConfirmation).build());


		var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
		emailService.sendPaymentSuccessEmail(paymentConfirmation.customerEmail(), customerName,
				paymentConfirmation.amount(), paymentConfirmation.orderRef()
		);
	}

	@KafkaListener(topics = "order-topic")
	public void consumeOrderConfirmationNotification( OrderConfirmation orderConfirmation ) throws MessagingException {
		log.info("Received Order Confirmation: {}", orderConfirmation);

		repository.save(Notification.builder().type(ORDER_CONFIRMATION).notificationDateTime(LocalDateTime.now()).orderConfirmation(orderConfirmation).build());


		var customerName =
				orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
		emailService.sendOrderConfirmationEmail(
				orderConfirmation.customer().email(),
				customerName,
				orderConfirmation.totalAmount(),
				orderConfirmation.orderRef(),
				orderConfirmation.products()
		);
	}
}
