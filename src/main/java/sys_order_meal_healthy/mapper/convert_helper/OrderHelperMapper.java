package sys_order_meal_healthy.mapper.convert_helper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import sys_order_meal_healthy.domain.entity.Customer;
import sys_order_meal_healthy.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class OrderHelperMapper {
    private final CustomerRepository repository;

    @Named("getCustomerById")
    public Customer getCustomerById(String phoneNumber) {
        return repository.findByIdOrThrow(phoneNumber);
    }
}
