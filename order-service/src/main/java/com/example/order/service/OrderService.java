package com.example.order.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.shared.common.events.InventoryResponseEvent;
import org.shared.common.events.OrderCreatedEvent;
import org.shared.common.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

	public void createOrder(Order order) {
		
		order.setStatus(OrderStatus.PROCESSING);
		order.setCreatedAt(LocalDateTime.now());
		UUID uuid = UUID.randomUUID();
		order.setOrderId(uuid.toString());
		
		for (OrderItem item : order.getItems()) {
		    item.setOrder(order);  // Set the back reference
		}
		
		//save order
		Order savedOrder = repository.save(order);
		
		//publish order
		publishOrderCreatedEvent(savedOrder);
		
	}

	private void publishOrderCreatedEvent(Order order) {
		OrderCreatedEvent event = new OrderCreatedEvent(
				order.getOrderId(),
				order.getCustometerId(),
				order.getTotal(),
				order.getStatus(),
				LocalDateTime.now());
		
		kafkaTemplate.send("order-created",order.getOrderId(),event);
		
	}

	public void handleInventoryResponse(InventoryResponseEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
