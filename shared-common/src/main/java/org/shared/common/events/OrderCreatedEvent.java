package org.shared.common.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.shared.common.model.OrderStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderCreatedEvent {
	
	private String orderId;
	private String customerId;
	private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime timestamp;
	
    @JsonCreator
    public OrderCreatedEvent(@JsonProperty("orderId") String orderId,
                            @JsonProperty("customerId") String customerId,
                            @JsonProperty("totalAmount") BigDecimal totalAmount,
                            @JsonProperty("status") OrderStatus status,
                            @JsonProperty("timestamp") LocalDateTime timestamp) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.timestamp = timestamp;
    }

	public String getOrderId() {
		return orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
    
}
