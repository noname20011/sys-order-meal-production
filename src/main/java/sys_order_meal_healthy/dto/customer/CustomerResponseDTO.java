package sys_order_meal_healthy.dto.customer;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO implements Serializable {

    private String fullName;
    private String phoneNumber;
    private String address;
    private String district;
    private Integer totalOrders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
