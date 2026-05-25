package sys_order_meal_healthy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sys_order_meal_healthy.dto.ResponseData;
import sys_order_meal_healthy.dto.customer.CustomerRequestDTO;
import sys_order_meal_healthy.dto.customer.CustomerResponseDTO;
import sys_order_meal_healthy.dto.order.OrderRequestDTO;
import sys_order_meal_healthy.dto.order.OrderResponseDTO;
import sys_order_meal_healthy.service.customer.CustomerService;

@RestController
@RequestMapping("/api/v1/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/")
    public ResponseData<CustomerResponseDTO> getCustomer(@RequestParam String phoneNumber) {
        CustomerResponseDTO data = customerService.getUserByPhoneNumber(phoneNumber);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Get customer successfully!", data);
    }
}
