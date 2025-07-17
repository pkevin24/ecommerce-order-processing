package com.example.order.kafka;

import org.shared.common.events.InventoryCheckEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryEventProducer.class);
	
	private static final String INVENTORY_CHECK_TOPIC = "inventory-check";
	
	@Autowired
	private KafkaTemplate<String, InventoryCheckEvent> kafkaTemplate;
	
	public void sendInventoryCheckEvent(InventoryCheckEvent event) {
		logger.info("Sending inventory check event for order: {}", event.getOrderId());
		kafkaTemplate.send(INVENTORY_CHECK_TOPIC, event.getOrderId(), event);
	}


}
