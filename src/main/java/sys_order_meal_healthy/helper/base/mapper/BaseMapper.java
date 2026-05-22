package sys_order_meal_healthy.helper.base.mapper;

import java.util.List;

public interface BaseMapper<Entity, RequestDTO, ResponseDTO> {

    Entity mapToEntity(RequestDTO dto);

    ResponseDTO mapToResponseDto(Entity entity);


    List<Entity> mapToListEntities(List<RequestDTO> dtos);

    List<ResponseDTO> mapToListResponseDtos(List<Entity> entities);
}
