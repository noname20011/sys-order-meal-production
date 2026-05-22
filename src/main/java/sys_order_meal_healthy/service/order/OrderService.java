package sys_order_meal_healthy.service.order;

import sys_order_meal_healthy.dto.order.OrderRequestDTO;
import sys_order_meal_healthy.dto.order.OrderResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderResponseDTO getOrderById(String orderId);
    OrderResponseDTO addOrder(OrderRequestDTO dto);
    void deleteOrder(String orderId);
    List<OrderResponseDTO> getOrdersByCustomerId(String customerId);
    List<OrderResponseDTO> getAllOrders();
    List<OrderResponseDTO> getAllOrdersByFilterTime (LocalDate startDate, LocalDate endDate);
}
