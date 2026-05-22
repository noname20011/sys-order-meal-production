package sys_order_meal_healthy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import sys_order_meal_healthy.domain.entity.Order;
import sys_order_meal_healthy.dto.order.OrderRequestDTO;
import sys_order_meal_healthy.dto.order.OrderResponseDTO;
import sys_order_meal_healthy.helper.base.mapper.BaseMapper;
import sys_order_meal_healthy.mapper.convert_helper.jsonToMapHelper;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {jsonToMapHelper.class})
public interface OrderMapper extends BaseMapper<Order, OrderRequestDTO, OrderResponseDTO> {

    @Override
    Order mapToEntity(OrderRequestDTO requestDTO);

    @Override
    @Mapping(target = "phoneNumber", source = "orderResult.customer.phoneNumber")
    @Mapping(target = "fullName", source = "orderResult.customer.fullName")
    OrderResponseDTO mapToResponseDto(Order orderResult);
}
