package com.ashutosh.order_service.dto;

import com.ashutosh.order_service.entity.OrderStatus;

//DTO stands for Data Transfer Object it's a simple java class that is used to transfer data between different layers of your application usually between the controller and the service layer.
//When we want to hide internal details of entity class (password, internal id etc).
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private Long restaurantId;
    private OrderStatus status;
    private Long totalAmount;

    public OrderDto(Long orderId,Long customerId, Long restaurantId, OrderStatus status, Long totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Long totalAmount) { this.totalAmount = totalAmount; }
}