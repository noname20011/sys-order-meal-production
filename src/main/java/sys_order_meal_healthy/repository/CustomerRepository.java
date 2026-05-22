package sys_order_meal_healthy.repository;

import org.springframework.stereotype.Repository;
import sys_order_meal_healthy.domain.entity.Customer;
import sys_order_meal_healthy.helper.base.repository.BaseRepository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
