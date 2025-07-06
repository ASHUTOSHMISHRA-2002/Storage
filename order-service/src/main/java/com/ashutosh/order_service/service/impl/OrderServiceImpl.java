package com.ashutosh.order_service.service.impl;

import com.ashutosh.order_service.dto.OrderDto;
import com.ashutosh.order_service.entity.Order;
import com.ashutosh.order_service.entity.OrderStatus;
import com.ashutosh.order_service.exception.ResourceNotFoundException;
import com.ashutosh.order_service.repository.OrderRepository;
import com.ashutosh.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<OrderDto> placeOrder(OrderDto dto) {
        Order order = new Order();
        order.setCustomerId(dto.getCustomerId()); // here we are actually mapping the order entity with orderdto entity
        order.setRestaurantId(dto.getRestaurantId());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(OrderStatus.ACCEPTED); // or PENDING as default

        Order savedOrder = orderRepository.save(order); // we are persisting the above changes into our database

        OrderDto response = new OrderDto(
                savedOrder.getOrderId(),
                savedOrder.getCustomerId(),
                savedOrder.getRestaurantId(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount()
        ); // we are converting the order entity object into orderdto object because it's a better way to write code

        return new ResponseEntity<>(response, HttpStatus.CREATED); //here we are passing the response body/ object and a status code as CREATED HTTP 201 (resource successfully created).
    }

    @Override
    public ResponseEntity<OrderDto> trackOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        //Here we are finding an order from the database by its ID once I found it will assign it to the order variable or else it will throw custom exception ResourceNotFoundException
        //findById returns an optional<order> object, it might contain an order, or it might be empty (it not found).
        OrderDto response = new OrderDto(
                order.getOrderId(),
                order.getCustomerId(),
                order.getRestaurantId(),
                order.getStatus(),
                order.getTotalAmount()
        );
        //Here we are converting order into response type

        return ResponseEntity.ok(response);
        //it will return status code of 200 ok
    }

    @Override
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> responseList = orderRepository.findAll()
                .stream()
                .map(order -> new OrderDto(
                        order.getOrderId(),
                        order.getCustomerId(),
                        order.getRestaurantId(),
                        order.getStatus(),
                        order.getTotalAmount()
                ))
                .collect(Collectors.toList());
//Here records fetches from the db and
//.stream() turn that into a stream and
// convert each .map(order -> orderDto) order into an orderdto
// then .collect(Collectors.toList()) collects the results into a new List<OrderDto>
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<OrderDto> updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        OrderStatus newStatus;
        //Here we have created a variable newStatus of OrderStatus type
        try {
            newStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
//Here im using orderStatus.valueOf(status.toUpperCase()) to safely convert a user provided status string into enum constant and storing it in newStatus variable.
        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);

        OrderDto response = new OrderDto(
                updated.getOrderId(),
                updated.getCustomerId(),
                updated.getRestaurantId(),
                updated.getStatus(),
                updated.getTotalAmount()
        );
//We are converting order type update into orderdto type and storing it in a variable respose and returring as a response type  with status code 200 as ok.
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<OrderDto> cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("Order cannot be cancelled at this stage: " + order.getStatus());
        }
//We can only cancel a order if its state is pending or accepted or else it will throw exception
        order.setStatus(OrderStatus.CANCELLED);
        Order cancelled = orderRepository.save(order);

        OrderDto response = new OrderDto(
                cancelled.getOrderId(),
                cancelled.getCustomerId(),
                cancelled.getRestaurantId(),
                cancelled.getStatus(),
                cancelled.getTotalAmount()
        );

        return ResponseEntity.ok(response);
    }
}

//@Service- It is a stereotype annotation in spring which marks a class as a service layer bean where our business lives.
//When spring boot starts it automatically detects classes annotated with @Service.
//Stream() -> It is a method in java that converts a collection (like a List or Set) into a Stream - which is a pipeline of elements that you can process one by one using functional style operations like filter,map,forEach,collect etc