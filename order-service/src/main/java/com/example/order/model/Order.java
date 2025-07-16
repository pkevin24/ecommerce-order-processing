package com.example.order.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
@Table(name = "orders", schema = "order_mgmt")
public class Order {

	@Id
	private String orderId;
	
	private String custometerId;
	
	private OrderStatus status;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> items;
	
	private BigDecimal subtotal;
	
    private BigDecimal tax;
    
    private BigDecimal shippingCost;
    
    private BigDecimal total;
    
    private String shippingAddress;
    
    private String billingAddress;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
