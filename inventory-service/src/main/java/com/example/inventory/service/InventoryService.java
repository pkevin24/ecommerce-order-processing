package com.example.inventory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.shared.common.events.InventoryCheckEvent;
import org.shared.common.events.InventoryCheckEvent.InventoryItem;
import org.shared.common.events.InventoryResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;

	public void checkStockAvailability(InventoryCheckEvent event) {
		List<InventoryItem>  items = event.getItems();
		
		List<String> productIds = items.stream()
				.map(item -> item.getProductId())
				.collect(Collectors.toList());
		
		List<Inventory> inStock = inventoryRepository.findByProductIdIn(productIds);

		Map<String, Integer> inventoryMap = inStock.stream()
		        .collect(Collectors.toMap(
		            Inventory::getProductId,      
		            Inventory::getAvailableQuantity 
		        ));

		Map<String, Integer> orderPlacedMap = items.stream()
				.collect(Collectors.toMap(
						InventoryItem::getProductId,      
						InventoryItem::getQuantity 
			        ));
		
		List<InventoryResponseEvent.ItemAvailability> availabilityList = orderPlacedMap.entrySet().stream()
		        .map(entry -> {
		            String productId = entry.getKey();
		            int requestedQuantity = entry.getValue();
		            int availableQuantity = inventoryMap.getOrDefault(productId, 0);
		            boolean sufficient = availableQuantity >= requestedQuantity;

		            return new InventoryResponseEvent.ItemAvailability(
		                    productId,
		                    requestedQuantity,
		                    availableQuantity,
		                    sufficient
		            );
		        })
		        .collect(Collectors.toList());
		
		boolean allItemsAvailable = isOrderFulfillable(inventoryMap, orderPlacedMap);
		
		//Create a response
		InventoryResponseEvent response = new InventoryResponseEvent(event.getOrderId(), allItemsAvailable, availabilityList, LocalDateTime.now());
		
		
		
		
		
	}
	
	
	public boolean isOrderFulfillable(Map<String, Integer> inventoryMap, Map<String, Integer> orderPlacedMap) {
	    for (Map.Entry<String, Integer> entry : orderPlacedMap.entrySet()) {
	        String productId = entry.getKey();
	        int requiredQty = entry.getValue();

	        int availableQty = inventoryMap.getOrDefault(productId, 0);

	        if (availableQty < requiredQty) {
	            return false; // Not enough stock
	        }
	    }
	    return true; // All items available
	}


}
