package sys_order_meal_healthy.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class CustomerResponseDTO implements Serializable {

    private UUID id;
    private String fullName;
    private String phoneNumber;
    private String detailAddress;
    private String district;
    private Integer totalOrders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
