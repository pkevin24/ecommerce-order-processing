package org.shared.common.events;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryResponseEvent {
	
	private String orderId;
	private boolean available;
	private List<ItemAvailability> itemAvailabilities;
	private LocalDateTime timestamp;
	
	@JsonCreator
    public InventoryResponseEvent(@JsonProperty("orderId") String orderId,
                                 @JsonProperty("available") boolean available,
                                 @JsonProperty("itemAvailabilities") List<ItemAvailability> itemAvailabilities,
                                 @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.orderId = orderId;
        this.available = available;
        this.itemAvailabilities = itemAvailabilities;
        this.timestamp = timestamp;
    }

	
	public String getOrderId() { return orderId; }
    public boolean isAvailable() { return available; }
    public List<ItemAvailability> getItemAvailabilities() { return itemAvailabilities; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    public static class ItemAvailability {
        private String productId;
        private int requestedQuantity;
        private int availableQuantity;
        private boolean sufficient;

        @JsonCreator
        public ItemAvailability(@JsonProperty("productId") String productId,
                               @JsonProperty("requestedQuantity") int requestedQuantity,
                               @JsonProperty("availableQuantity") int availableQuantity,
                               @JsonProperty("sufficient") boolean sufficient) {
            this.productId = productId;
            this.requestedQuantity = requestedQuantity;
            this.availableQuantity = availableQuantity;
            this.sufficient = sufficient;
        }

        public String getProductId() { return productId; }
        public int getRequestedQuantity() { return requestedQuantity; }
        public int getAvailableQuantity() { return availableQuantity; }
        public boolean isSufficient() { return sufficient; }
    }
    
}
