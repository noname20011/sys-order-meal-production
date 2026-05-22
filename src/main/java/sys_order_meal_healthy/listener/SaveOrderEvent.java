package sys_order_meal_healthy.listener;

import sys_order_meal_healthy.dto.order.OrderRequestDTO;

public record SaveOrderEvent(OrderRequestDTO dto) {}
