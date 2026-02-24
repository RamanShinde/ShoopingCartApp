package com.example.OrderService.Service;

import com.example.OrderService.DTO.CreateOrderRequest;
import com.example.OrderService.DTO.OrderResponse;
import com.example.OrderService.Entity.Order;
import com.example.OrderService.Enum.Status;
import com.example.OrderService.Repository.OrderRepo;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private  OrderRepo orderRepository;

    public OrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProduct(request.getProduct());
        order.setQuantity(request.getQuantity());
        order.setStatus(Status.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        return ordertoDTOResponse(saved);
    }

    private OrderResponse ordertoDTOResponse(Order saved) {
        OrderResponse response=new OrderResponse();
        response.setOrderId(saved.getId());
        response.setUserId(saved.getUserId());
        response.setProduct(saved.getProduct());
        response.setQuantity(saved.getQuantity());
        response.setStatus(String.valueOf(saved.getStatus()));
        return response;
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {

        return orderRepository.findById(userId)
                .stream()
                .map(o -> new OrderResponse(
                        o.getId(),
                        o.getUserId(),
                        o.getProduct(),
                        o.getQuantity(),
                        String.valueOf(o.getStatus())
                ))
                .toList();
    }
}
