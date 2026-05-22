package sys_order_meal_healthy.domain.entity;

import sys_order_meal_healthy.domain.entity.base.BaseEntityHasId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_detail_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem extends BaseEntityHasId {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    private LocalDate deliveryDate; // Ngày giao cụ thể
    private String mealSession; // MORNING, NOON, EVENING
}
