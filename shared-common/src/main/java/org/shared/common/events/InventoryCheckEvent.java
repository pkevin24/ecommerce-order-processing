package org.shared.common.events;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryCheckEvent {

	private String orderId;
	private List<InventoryItem> items;
	private LocalDateTime timestamp;
	
	@JsonCreator
    public InventoryCheckEvent(@JsonProperty("orderId") String orderId, 
                              @JsonProperty("items") List<InventoryItem> items,
                              @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.orderId = orderId;
        this.items = items;
        this.timestamp = timestamp;
    }
	
	public String getOrderId() { return orderId; }
    public List<InventoryItem> getItems() { return items; }
    public LocalDateTime getTimestamp() { return timestamp; }
	
	public static class InventoryItem{
		private String productId;
		private int quantity;
		
		@JsonCreator
		public InventoryItem(@JsonProperty("productId") String productId, 
                @JsonProperty("quantity") int quantity) {
		 this.productId = productId;
		 this.quantity = quantity;
		}
		
		public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }
		
	}
}
