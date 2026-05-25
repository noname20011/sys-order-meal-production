package sys_order_meal_healthy.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sys_order_meal_healthy.dto.ResponseData;
import sys_order_meal_healthy.dto.customer.CustomerRequestDTO;
import sys_order_meal_healthy.dto.customer.CustomerResponseDTO;
import sys_order_meal_healthy.dto.order.OrderRequestDTO;
import sys_order_meal_healthy.dto.order.OrderResponseDTO;
import sys_order_meal_healthy.service.customer.CustomerService;
import sys_order_meal_healthy.service.order.OrderService;

@RestController
@RequestMapping("/api/v1/order/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/")
    public ResponseData<OrderResponseDTO> saveOrder(@ModelAttribute @Valid OrderRequestDTO userRequestDTO) {
        OrderResponseDTO data = orderService.addOrder(userRequestDTO);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Create successfully!", data);
    }


    @DeleteMapping("/{orderId}")
    public ResponseData<?> deleteOrder (@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseData<>(HttpStatus.OK.value(), "Delete order successfully!");
    }
}
