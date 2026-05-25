package sys_order_meal_healthy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import sys_order_meal_healthy.domain.entity.Customer;
import sys_order_meal_healthy.domain.entity.Order;
import sys_order_meal_healthy.dto.customer.CustomerRequestDTO;
import sys_order_meal_healthy.dto.customer.CustomerResponseDTO;
import sys_order_meal_healthy.dto.order.OrderResponseDTO;
import sys_order_meal_healthy.helper.base.mapper.BaseMapper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper extends BaseMapper<Customer, CustomerRequestDTO, CustomerResponseDTO> {

    @Override
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "address", source = "address")
    CustomerResponseDTO mapToResponseDto(Customer orderResult);
}
