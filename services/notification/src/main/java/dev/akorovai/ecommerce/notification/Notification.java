package dev.akorovai.ecommerce.notification;

import dev.akorovai.ecommerce.kafka.order.OrderConfirmation;
import dev.akorovai.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Notification {
	@Id
	private String id;
	private NotificationType type;
	private LocalDateTime notificationDateTime;
	private OrderConfirmation orderConfirmation;
	private PaymentConfirmation paymentConfirmation;
}
