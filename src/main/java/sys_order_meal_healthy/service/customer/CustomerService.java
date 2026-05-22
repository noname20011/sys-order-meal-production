package sys_order_meal_healthy.service.customer;

import sys_order_meal_healthy.domain.entity.Customer;
import sys_order_meal_healthy.dto.customer.CustomerRequestDTO;
import sys_order_meal_healthy.dto.customer.CustomerResponseDTO;

public interface CustomerService {
    CustomerResponseDTO getUserByPhoneNumber(String phoneNumber);
    CustomerResponseDTO addUser(CustomerRequestDTO dto);
    Customer addUserEntity(CustomerRequestDTO dto);
    CustomerResponseDTO putUser(String phoneNumber, CustomerRequestDTO dto);
    void deleteUser(String phoneNumber);
}
