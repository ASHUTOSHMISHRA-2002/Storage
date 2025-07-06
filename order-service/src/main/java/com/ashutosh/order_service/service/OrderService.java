package com.ashutosh.order_service.service;

import com.ashutosh.order_service.dto.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDto> placeOrder(OrderDto dto);
    ResponseEntity<OrderDto> trackOrder(Long orderId);
    ResponseEntity<List<OrderDto>> getAllOrders();
    ResponseEntity<OrderDto> updateOrderStatus(Long orderId, String status);
    ResponseEntity<OrderDto> cancelOrder(Long orderId);
}
//This interface is defining the business logic operations related to orders, It declares what functionalities your order modules should have,
// like: placing order,Tracking order, Viewing all order, update,cancel etc
