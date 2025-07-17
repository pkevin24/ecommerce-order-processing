package com.example.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory", schema = "inventory_mgmt")
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false, unique = true, length = 50)
    private String productId;
    
    @Column(name = "product_name", nullable = false, length = 255)
    private String productName;
    
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity = 0;
    
    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity = 0;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public Inventory() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Inventory(String productId, String productName, BigDecimal unitPrice, 
                    Integer availableQuantity, Integer reservedQuantity) {
        this();
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
    }
    
    // Complete constructor with all fields
    public Inventory(Long id, String productId, String productName, BigDecimal unitPrice,
                    Integer availableQuantity, Integer reservedQuantity, 
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
    
    public Integer getReservedQuantity() {
        return reservedQuantity;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    
    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Business logic methods
    public Integer getTotalQuantity() {
        return availableQuantity + reservedQuantity;
    }
    
    public boolean isAvailable(Integer requestedQuantity) {
        return availableQuantity >= requestedQuantity;
    }
    
    public void reserveQuantity(Integer quantity) {
        if (isAvailable(quantity)) {
            this.availableQuantity -= quantity;
            this.reservedQuantity += quantity;
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Insufficient available quantity");
        }
    }
    
    public void releaseReservedQuantity(Integer quantity) {
        if (reservedQuantity >= quantity) {
            this.reservedQuantity -= quantity;
            this.availableQuantity += quantity;
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Insufficient reserved quantity");
        }
    }
    
    public void confirmReservation(Integer quantity) {
        if (reservedQuantity >= quantity) {
            this.reservedQuantity -= quantity;
            this.updatedAt = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Insufficient reserved quantity");
        }
    }
    
    // toString method
    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", availableQuantity=" + availableQuantity +
                ", reservedQuantity=" + reservedQuantity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Inventory inventory = (Inventory) o;
        
        return productId != null ? productId.equals(inventory.productId) : inventory.productId == null;
    }
    
    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
    }
}