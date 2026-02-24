package com.example.OrderService.DTO;

import com.example.OrderService.Enum.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String product;
    private Integer quantity;
    private String status;

}
