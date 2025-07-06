package com.ashutosh.order_service.controller;

import com.ashutosh.order_service.dto.OrderDto;
import com.ashutosh.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//It is used to build RESTful web services in java.It is a combination of 2 annotation @controller  @ResponseBody
@RequestMapping("/orders")
// is used to map HTTP requests to handler methods in your controller. It tells Spring which method to call when a particular URL is requested.
public class OrderController {

    private final OrderService orderService;// here we are injecting orderService as dependency injection.

    @Autowired //We are performing constructor based dependency injection here which is a better approach
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeorders")
//Here we are using PostMapping and which is used to map Http Post request to a specific method in our controller (it is Basically creating a data)
    public ResponseEntity<OrderDto> placeOrder(OrderDto orderDto) {
        ResponseEntity<OrderDto> createdOrder = orderService.placeOrder(orderDto);
        return createdOrder;
    }

    @GetMapping("/{orderId}")
//Here we are using GetMapping and which is used to map Http Get request to a specific method in our controller (it is basically fetch the data)
    public ResponseEntity<OrderDto> trackOrder(@PathVariable("orderId") Long orderId) {
        ResponseEntity<OrderDto> orderDto = orderService.trackOrder(orderId);
        return orderDto;
    }

    @GetMapping("/getallorders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        ResponseEntity<List<OrderDto>> orders = orderService.getAllOrders();
        return orders;
    }

    @PutMapping("/{orderId}/status")
    //Here we are using PutMapping which is used to map Http Put request to a specific method in our controller (it is basically used to update a data)
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("status") String status) {
        ResponseEntity<OrderDto> updatedOrder = orderService.updateOrderStatus(orderId, status);
        return updatedOrder;
    }

    //Optional
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable("orderId") Long OrderId) {
        ResponseEntity<OrderDto> orderDto = orderService.cancelOrder(OrderId);
        return orderDto;
    }
}


//ResponseEntity- It is a spring class that represents the entire HTTP response.It allows us to customize the response body, the HTTP status code (like 200 ok ot 404 not found).
//PathVariable- It is used to extract a value from the URL path.(It allows controller method to read parts of the URL and use them as method arguments).
//RequestParam- It is used to get data from the URL query string (the part after ?).