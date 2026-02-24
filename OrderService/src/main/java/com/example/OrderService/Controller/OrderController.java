package com.example.OrderService.Controller;

import com.example.OrderService.DTO.CreateOrderRequest;
import com.example.OrderService.DTO.OrderResponse;
import com.example.OrderService.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/Order")
public class OrderController {

    private final OrderService orderService;
    private final RestTemplate restTemplate;

    public OrderController(OrderService orderService,RestTemplate template) {
        this.restTemplate=template;
        this.orderService = orderService;
    }

    @GetMapping("/getMesg")
    public String getMsg(){
        return "Welcome to order page";
    }


    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        Boolean isValidUser = restTemplate.getForObject(
                "http://localhost:8081/deliverApp/UserService/internal/validate/" + request.getUserId(),
                Boolean.class
        );

        if (Boolean.FALSE.equals(isValidUser)) {
            throw new RuntimeException("User does not exist");
        }
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
