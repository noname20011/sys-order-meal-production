package sys_order_meal_healthy.repository;

import sys_order_meal_healthy.domain.entity.Order;
import sys_order_meal_healthy.helper.base.repository.BaseRepository;

import java.util.Optional;

public interface OrderRepository extends BaseRepository<Order, String> {
    Optional<Order> findByCustomerPhoneNumber(String orderNumber);
}
