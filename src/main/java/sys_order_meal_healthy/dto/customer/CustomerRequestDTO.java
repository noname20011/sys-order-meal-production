package sys_order_meal_healthy.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class CustomerRequestDTO implements Serializable {

    @Pattern(
            regexp = "^(0|\\+84)[3|5|7|9][0-9]{8}$",
            message = "PhoneNumber not valid!"
    )
    private String phoneNumber;

    @NotBlank(message = "Field FullName must be not blank!")
    private String fullName;

    @NotBlank(message = "Field Address must be not blank!")
    private String address;

    @NotBlank(message = "Field District must be not blank!")
    private String district;
}
