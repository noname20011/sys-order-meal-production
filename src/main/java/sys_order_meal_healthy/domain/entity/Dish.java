package sys_order_meal_healthy.domain.entity;

import sys_order_meal_healthy.domain.entity.base.BaseEntityHasId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_dish")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dish extends BaseEntityHasId {
    private String dishName;
    private Double price;
    private String imageUrl;
}
