package com.example.order.orchestrator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.shared.common.events.InventoryCheckEvent;
import org.shared.common.events.InventoryResponseEvent;
import org.shared.common.events.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.order.kafka.InventoryEventProducer;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class OrderOrchestrator {

	private static final Logger logger = LoggerFactory.getLogger(OrderOrchestrator.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private InventoryEventProducer inventoryEventProducer;

	@KafkaListener(topics = "order-created", groupId = "order-orchestrator-group")
	public void handleOrderCreated(OrderCreatedEvent event) {
		logger.info("ORCHESTRATOR: Starting order processing for order: {}", event.getOrderId());

		Order order = getOrder(event);
		orderRepository.save(order);

		// check Inventory
		checkInventory(order);
	}

	@KafkaListener(topics = "inventory-response", groupId = "inventory-service-group")
	public void handleInventoryResponse(InventoryResponseEvent event) {
		logger.info("Received inventory response for order: {}, available: {}", event.getOrderId(),
				event.isAvailable());

//	        orderService.handleInventoryResponse(event);
	}

	private void checkInventory(Order order) {
		logger.info("ORCHESTRATOR: Step 1 - Checking inventory for order: {}", order.getOrderId());

		// Null check for order items
		if (order.getItems() == null || order.getItems().isEmpty()) {
			logger.warn("Order {} has no items to check inventory for", order.getOrderId());
			return;
		}

		// Convert OrderItem to InventoryCheckEvent.InventoryItem
		List<InventoryCheckEvent.InventoryItem> items = order.getItems().stream().filter(item -> item != null)
				.map(item -> new InventoryCheckEvent.InventoryItem(item.getProductId(), item.getQuantity()))
				.collect(Collectors.toList());

		// Create and send the inventory check event
		InventoryCheckEvent event = new InventoryCheckEvent(order.getOrderId(), items, LocalDateTime.now());

		try {
			inventoryEventProducer.sendInventoryCheckEvent(event);
			logger.info("Successfully sent inventory check event for order: {}", order.getOrderId());
		} catch (Exception e) {
			logger.error("Failed to send inventory check event for order: {}", order.getOrderId(), e);
			throw new RuntimeException("Failed to send inventory check event", e);
		}
	}

	@Transactional
	public Order getOrder(OrderCreatedEvent event) {
		Order order = orderRepository.findById(event.getOrderId())
				.orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));
		order.getItems().size();
		return order;
	}

}
