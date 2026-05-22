package sys_order_meal_healthy.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sys_order_meal_healthy.domain.constants.PaidEnum;
import sys_order_meal_healthy.domain.entity.Customer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class OrderResponseDTO implements Serializable {

    private String id;
    private String phoneNumber;
    private String fullName;
    private String mealPackage;
    private Integer totalPrice;
    private String paymentProofUrl;
    private PaidEnum paidBy;
    private Double shipFee;

    private String metadataOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
