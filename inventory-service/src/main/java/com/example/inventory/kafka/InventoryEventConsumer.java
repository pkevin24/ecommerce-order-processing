package com.example.inventory.kafka;

import org.shared.common.events.InventoryCheckEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.inventory.service.InventoryService;

@Component
public class InventoryEventConsumer {

	
	private static final Logger logger = LoggerFactory.getLogger(InventoryEventConsumer.class);
	
	@Autowired
	private InventoryService inventoryService;
	
	
	@KafkaListener(topics = "inventory-check", groupId = "inventory-service-group")
	public void handleInventoryCheck(InventoryCheckEvent event) {
		logger.info(" INVENTORY: Received inventory check request for order: {}", event.getOrderId());
		try {
			inventoryService.checkStockAvailability(event);
		}catch(Exception e) {
			
		}
	}
	
	
}
