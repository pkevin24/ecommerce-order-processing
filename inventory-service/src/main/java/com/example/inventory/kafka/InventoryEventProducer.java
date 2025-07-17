package com.example.inventory.kafka;

import org.shared.common.events.InventoryResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventProducer {

	private static final Logger logger = LoggerFactory.getLogger(InventoryEventProducer.class);
	
	private static final String INVENTORY_RESPONSE_TOPIC = "inventory-response";
	
	@Autowired
	private KafkaTemplate<String, InventoryResponseEvent> kafkaTemplate;
	
	public void sendInventoryResponseEvent(InventoryResponseEvent event) {
		logger.info("Sending inventory check event for order: {}", event.getOrderId());
		kafkaTemplate.send(INVENTORY_RESPONSE_TOPIC, event.getOrderId(), event);
	}
}
