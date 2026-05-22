package sys_order_meal_healthy.repository;

import org.springframework.stereotype.Repository;
import sys_order_meal_healthy.domain.entity.User;
import sys_order_meal_healthy.helper.base.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPassword(String password);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
